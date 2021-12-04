package com.hero.instadog.api

import com.hero.instadog.repository.model.Breed

interface BreedsService {
    suspend fun getBreeds(): List<Breed>
}