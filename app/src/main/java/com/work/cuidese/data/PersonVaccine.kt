package com.work.cuidese.data

import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "personvaccine",
        primaryKeys = ["personId", "vaccineId"],
        indices = [Index(value = ["personId"]), Index(value = ["vaccineId"])]
        )

data class PersonVaccine (
    var personId: Int = 0,
    var vaccineId: Int = 0
)



