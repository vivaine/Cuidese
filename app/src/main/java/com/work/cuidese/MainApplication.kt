package com.work.cuidese

import android.app.Application
import com.work.cuidese.data.AppDatabase
import com.work.cuidese.data.PersonRepository
import com.work.cuidese.data.VaccineRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@HiltAndroidApp
class MainApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getInstance(this)}
    val personRepository by lazy { PersonRepository(database.personDao(), database.personVaccineDao())}
    val vaccineRepository by lazy { VaccineRepository(database.vaccineDao()) }
}