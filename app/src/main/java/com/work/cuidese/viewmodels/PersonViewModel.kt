package com.work.cuidese.viewmodels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.work.cuidese.data.Person
import com.work.cuidese.data.PersonRepository
import com.work.cuidese.data.PersonWithVaccines
import com.work.cuidese.data.Vaccine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class PersonViewModel(private val personRepository: PersonRepository): ViewModel(){

    val allPeople: LiveData<List<Person>> = personRepository.getPeople().asLiveData()

    private fun insert(person: Person): Long= runBlocking {
       personRepository.insert(person)
    }

    fun insertPerson(personName: String?, personBirth: String?, personGender: Int?): Long {
        if (personName == null || personBirth == null || personGender == null) {
            return 0
        }
        val localBirth : Calendar = Calendar.getInstance()
        localBirth.timeInMillis = personBirth.toLong()
        val person = Person(name = personName, birth = localBirth, gender = personGender)
        return insert(person)
    }

    fun alterPerson(id: Int?, personName: String?, personBirth: String?, personGender: Int?) = viewModelScope.launch {
        if (id!=null && personName != null && personBirth != null && personGender != null) {
            val localBirth : Calendar = Calendar.getInstance()
            localBirth.timeInMillis = personBirth.toLong()
            val person = Person(personId=id, name = personName, birth = localBirth, gender = personGender)
            personRepository.alter(person)
        }
    }

    suspend fun insertVaccine(personId: Int, vaccineId: Int) {
        personRepository.insertVaccine(personId, vaccineId)
    }

    fun getPersonWithVaccine(personId: Int): LiveData<List<Vaccine>> {
       return personRepository.getPersonWithVaccine(personId)

    }
}

class PersonViewModelFactory(private val personRepository: PersonRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonViewModel(personRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}