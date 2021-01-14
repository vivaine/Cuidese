package com.work.cuidese

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.cuidese.adapters.PersonListAdapter
import com.work.cuidese.adapters.VaccineListAdapter
import com.work.cuidese.data.Person
import com.work.cuidese.data.Vaccine
import com.work.cuidese.databinding.ActivityMainBinding
import com.work.cuidese.databinding.ActivityVaccineBinding
import com.work.cuidese.viewmodels.PersonViewModel
import com.work.cuidese.viewmodels.PersonViewModelFactory
import com.work.cuidese.viewmodels.VaccineViewModel
import com.work.cuidese.viewmodels.VaccineViewModelFactory

const val VACCINE_ID = "vaccine_id"
class VaccineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVaccineBinding
    private var currentId: Int = 0

    private val vaccineViewModel: VaccineViewModel by viewModels {
        VaccineViewModelFactory((application as MainApplication).vaccineRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVaccineBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var rvVaccine = binding.listVaccine
        val adapterVaccine = VaccineListAdapter { vaccine -> adapterOnClick(vaccine) }
        rvVaccine.adapter = adapterVaccine
        rvVaccine.layoutManager = LinearLayoutManager(this)

        vaccineViewModel.allVaccines.observe(this) { vaccine ->
            vaccine.let {
                adapterVaccine.submitList(it)
            }
        }
    }

    private fun adapterOnClick(vaccine: Vaccine) {
        val replyIntent = Intent()
        setResult(Activity.RESULT_OK, replyIntent)
        replyIntent.putExtra(VACCINE_ID, vaccine.id)
        finish()
    }
}