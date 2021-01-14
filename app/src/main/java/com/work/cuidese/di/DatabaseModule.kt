package com.work.cuidese.di

import android.content.Context
import com.work.cuidese.data.AppDatabase
import com.work.cuidese.data.PersonDao
import com.work.cuidese.data.VaccineDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePersonDao(appDatabase: AppDatabase): PersonDao {
        return appDatabase.personDao()
    }

    @Provides
    fun provideVaccineDao(appDatabase: AppDatabase): VaccineDao {
        return appDatabase.vaccineDao()
    }
}