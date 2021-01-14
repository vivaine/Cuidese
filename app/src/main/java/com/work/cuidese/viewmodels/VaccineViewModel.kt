package com.work.cuidese.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.work.cuidese.data.PersonRepository
import com.work.cuidese.data.Vaccine
import com.work.cuidese.data.VaccineRepository

class VaccineViewModel(private val vaccineRepository: VaccineRepository): ViewModel() {

    val allVaccines: LiveData<List<Vaccine>> = vaccineRepository.getVaccines().asLiveData()

}

class VaccineViewModelFactory(private val vaccineRepository: VaccineRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VaccineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VaccineViewModel(vaccineRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}