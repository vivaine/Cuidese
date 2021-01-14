package com.work.cuidese.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import java.util.concurrent.Flow

data class PersonWithVaccines (
    @Embedded val person: Person,
    @Relation(
        parentColumn = "id",
        entity = Vaccine::class,
        entityColumn = "id",
        associateBy = Junction(
            value = PersonVaccine::class,
            parentColumn = "personId",
            entityColumn = "vaccineId"
        )
    )
    public val vaccines: List<Vaccine>
)
