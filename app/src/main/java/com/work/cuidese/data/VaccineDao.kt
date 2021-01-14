package com.work.cuidese.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccineDao {
    @Query("SELECT * FROM vaccine ORDER BY name")
    fun getVaccines(): Flow<List<Vaccine>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vaccines: List<Vaccine>)
}