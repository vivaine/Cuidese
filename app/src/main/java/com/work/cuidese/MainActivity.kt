package com.work.cuidese

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.cuidese.adapters.PersonListAdapter
import com.work.cuidese.data.Person
import com.work.cuidese.databinding.ActivityMainBinding
import com.work.cuidese.viewmodels.PersonViewModel
import com.work.cuidese.viewmodels.PersonViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private val newPersonActivityRequestCode = 1
    private val editPersonActivityRequestCode = 2

    private lateinit var binding: ActivityMainBinding
    private val personViewModel: PersonViewModel by viewModels {
        PersonViewModelFactory((application as MainApplication).personRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerViewPerson = binding.listPerson
        val adapterPerson = PersonListAdapter{person -> adapterOnClick(person)}
        recyclerViewPerson.adapter = adapterPerson
        recyclerViewPerson.layoutManager = LinearLayoutManager(this)

        personViewModel.allPeople.observe(this) { people ->
            people.let {
                adapterPerson.submitList(it)
            }
        }

        val fabIncluir = binding.fabIncluirPessoa
        fabIncluir.setOnClickListener {
            val intent = Intent(this@MainActivity, PersonActivity::class.java)
            startActivityForResult(intent, newPersonActivityRequestCode)
        }
    }

    private fun adapterOnClick(person: Person) {
        val intent = Intent(this, PersonActivity::class.java)
        intent.putExtra(PERSON, person)
        startActivityForResult(intent, editPersonActivityRequestCode)
    }

    private fun adapterOnClickDelete(person: Person) {

    }
}