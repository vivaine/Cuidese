package com.work.cuidese.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Query("SELECT * FROM Person ORDER BY name")
    fun getPeople(): Flow<List<Person>>

    @Query("SELECT * FROM Person WHERE Id = :Id")
    fun getPersonById(Id: Int): Person?

    @Insert
    suspend fun insertPerson(person: Person): Long

    @Update
    suspend fun updatePerson(person: Person): Int
}