package com.work.cuidese.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vaccine")
data class Vaccine (
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    val name: String,
    val ageMonths: Int,
    val gender: Int //0-male/1-female/2-any
)