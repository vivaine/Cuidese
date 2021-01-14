package com.work.cuidese

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.cuidese.adapters.PersonListAdapter
import com.work.cuidese.adapters.VaccinesPersonListAdapter
import com.work.cuidese.data.Person
import com.work.cuidese.data.Vaccine
import com.work.cuidese.databinding.ActivityPersonBinding
import com.work.cuidese.viewmodels.PersonViewModel
import com.work.cuidese.viewmodels.PersonViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

const val PERSON_ID = "id"
const val PERSON_NAME = "name"
const val PERSON_BIRTH = "birth"
const val PERSON_GENDER = "gender"
const val PERSON = "person"

class PersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonBinding
    val cal = Calendar.getInstance()
    private var currentGender: Int = 0
    private var currentId: Int = 0
    private lateinit var list: LiveData<List<Vaccine>>
    private val adapterVaccines = VaccinesPersonListAdapter()

    private val personViewModel: PersonViewModel by viewModels<PersonViewModel> {
        PersonViewModelFactory((application as MainApplication).personRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val currentPerson = intent?.getSerializableExtra(PERSON)

        val recyclerViewVaccines = binding.rvVaccinesPerson
        //val adapterVaccines = VaccinesPersonListAdapter()
        recyclerViewVaccines.adapter = adapterVaccines
        recyclerViewVaccines.layoutManager = LinearLayoutManager(this)

        binding.rdMasculino.isChecked = true
        if (currentPerson !=null) {
            currentPerson as Person
            currentId = currentPerson.personId
            currentGender = currentPerson.gender
            binding.fabIncluiVacina.visibility = View.VISIBLE
            binding.txtName.setText(currentPerson.name)
            updateDateInView(currentPerson.birth.getTime())
            if (currentPerson.gender == 0) {
                binding.rdMasculino.isChecked = true
            } else {
                binding.rdFeminino.isChecked = true
            }
        }

        list = personViewModel.getPersonWithVaccine(currentId)

        list.observe(this) { people ->
            people.let {
                adapterVaccines.submitList(it)
            }
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(cal.getTime())
            }
        }

        binding.btBirthday.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@PersonActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        binding.btSave.setOnClickListener{
            if (binding.txtName.text.isNullOrEmpty()) {
                Toast.makeText(this, "Preencha o nome!", Toast.LENGTH_SHORT).show()
            } else if (binding.txtBirth.text.isNullOrEmpty()) {
                Toast.makeText(this, "Preencha o nascimento!", Toast.LENGTH_SHORT).show()
            } else {
                if (currentId != 0) {
                    personViewModel.alterPerson(currentId,
                        binding.txtName.text.toString(),
                        cal.timeInMillis.toString(),
                        currentGender
                    )
                } else {
                   currentId = personViewModel.insertPerson(
                       binding.txtName.text.toString(),
                       cal.timeInMillis.toString(),
                       currentGender).toInt()
                }
                binding.fabIncluiVacina.visibility = View.VISIBLE
            }
        }

        binding.fabIncluiVacina.setOnClickListener {
            val intent = Intent(this, VaccineActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (resultCode == Activity.RESULT_OK) {
            intentData?.let { reply ->
                val vaccineId = reply.getIntExtra(VACCINE_ID, 0)
                GlobalScope.launch {
                    personViewModel.insertVaccine(currentId, vaccineId)
                }
                list.removeObservers(this)
                list = personViewModel.getPersonWithVaccine(currentId)

                list.observe(this) { people ->
                    people.let {
                        adapterVaccines.submitList(it)
                    }
                }
            }
        }
    }

    private fun updateDateInView(data: Date) {
        val myFormat = "dd/MM/yyyy : HH:mm:ss" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.txtBirth.setText(sdf.format(data))
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                binding.rdMasculino.id ->
                    if (checked) {
                        currentGender = 0
                    }
                binding.rdFeminino.id ->
                    if (checked) {
                        currentGender = 1
                    }
            }
        }
    }
}