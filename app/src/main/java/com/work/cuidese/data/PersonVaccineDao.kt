package com.work.cuidese.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonVaccineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(join: PersonVaccine): Long

    @Transaction
    @Query("SELECT * FROM Person WHERE id=:personId")
    suspend fun getPeopleWithVaccines(personId: Int): PersonWithVaccines

}