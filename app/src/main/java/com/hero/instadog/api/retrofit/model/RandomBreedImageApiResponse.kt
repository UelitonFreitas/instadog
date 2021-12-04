package com.hero.instadog.api.retrofit.model

import com.google.gson.annotations.SerializedName

data class RandomBreedImageApiResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: String

)