package com.work.cuidese.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepository @Inject constructor(
    private val personDao: PersonDao, private var personVaccineDao: PersonVaccineDao
) {

    suspend fun insert(person: Person): Long {
        return personDao.insertPerson(person)
    }

    suspend fun alter(person: Person) {
        personDao.updatePerson(person)
    }

    suspend fun insertVaccine(personId: Int, vaccineId: Int) {
        val personVaccine = PersonVaccine(personId, vaccineId)
        personVaccineDao.insert(personVaccine)
    }

    fun getPeople() = personDao.getPeople()

    fun getPersonWithVaccine(personId: Int): LiveData<List<Vaccine>> {
        val list: LiveData<List<Vaccine>> = liveData {
            val data = personVaccineDao.getPeopleWithVaccines(personId)
            if (data!=null)
                emit(data.vaccines)
        }
        return list
    }
}