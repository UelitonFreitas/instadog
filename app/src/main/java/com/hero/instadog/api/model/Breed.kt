package com.hero.instadog.api.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["name"])
data class Breed(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("image_url")
    val imageUrl: String?
)

data class Root(

    @SerializedName("message")
    val message: Map<String, List<String>>,

    @SerializedName("status")
    val status: String

)