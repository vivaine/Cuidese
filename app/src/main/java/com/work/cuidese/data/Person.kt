package com.work.cuidese.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "person")
data class Person (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var personId: Int = 0,
    val name: String,
    val birth: Calendar = Calendar.getInstance(),
    val gender: Int = 0 // 0- male/1-female
): Serializable
