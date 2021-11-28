package com.hero.instadog.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.google.gson.annotations.SerializedName

@Entity
data class Breed(

    @PrimaryKey
    val name: String,

    val imageUrl: String?
)


@Entity
data class SubBreed(

    @PrimaryKey
    val name: String,

    val imageUrl: String?,

    val breedName: String

)

data class BreedWithSubBreeds(
    @Embedded val breed: Breed,

    @Relation(
        parentColumn = "name",
        entityColumn = "breedName"
    )

    val subBreeds: List<SubBreed>
)

