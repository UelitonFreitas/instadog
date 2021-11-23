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

data class Message(

    @SerializedName("greyhound")
    val greyhound: List<String>,

    @SerializedName("buhund")
    val buhund: List<String>,

    @SerializedName("deerhound")
    val deerhound: List<String>,

    @SerializedName("mountain")
    val mountain: List<String>,

    @SerializedName("retriever")
    val retriever: List<String>,

    @SerializedName("elkhound")
    val elkhound: List<String>,

    @SerializedName("springer")
    val springer: List<String>,

    @SerializedName("pointer")
    val pointer: List<String>,

    @SerializedName("bulldog")
    val bulldog: List<String>,

    @SerializedName("cattledog")
    val cattledog: List<String>,

    @SerializedName("collie")
    val collie: List<String>,

    @SerializedName("schnauzer")
    val schnauzer: List<String>,

    @SerializedName("australian")
    val australian: List<String>,

    @SerializedName("ovcharka")
    val ovcharka: List<String>,

    @SerializedName("mastiff")
    val mastiff: List<String>,

    @SerializedName("sheepdog")
    val sheepdog: List<String>,

    @SerializedName("corgi")
    val corgi: List<String>,

    @SerializedName("spaniel")
    val spaniel: List<String>,

    @SerializedName("ridgeback")
    val ridgeback: List<String>,

    @SerializedName("dane")
    val dane: List<String>,

    @SerializedName("waterdog")
    val waterdog: List<String>,

    @SerializedName("poodle")
    val poodle: List<String>,

    @SerializedName("hound")
    val hound: List<String>,

    @SerializedName("terrier")
    val terrier: List<String>,

    @SerializedName("bullterrier")
    val bullterrier: List<String>,

    @SerializedName("finnish")
    val finnish: List<String>,

    @SerializedName("pinscher")
    val pinscher: List<String>,

    @SerializedName("setter")
    val setter: List<String>,

    @SerializedName("frise")
    val frise: List<String>,

    @SerializedName("wolfhound")
    val wolfhound: List<String>

)


data class Root(

    @SerializedName("message")
    val message: Message,

    @SerializedName("status")
    val status: String

)