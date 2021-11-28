package com.hero.instadog.repository.model


data class Breed(val name: String, val imageUrl: String? = null, val subBreeds: List<SubBreed> = emptyList())

data class SubBreed(val name: String)