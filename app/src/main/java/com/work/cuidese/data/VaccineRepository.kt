package com.work.cuidese.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VaccineRepository @Inject constructor(
    private val vaccineDao: VaccineDao
){
    fun getVaccines() = vaccineDao.getVaccines()
}