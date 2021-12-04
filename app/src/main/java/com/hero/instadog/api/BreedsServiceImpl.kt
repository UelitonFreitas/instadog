package com.hero.instadog.api

import com.hero.instadog.api.retrofit.RetrofitBreedsService
import com.hero.instadog.repository.model.Breed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BreedsServiceImpl : BreedsService {

    companion object {
        const val baseUrl = "https://dog.ceo/api/"
    }

    private val breedsService: RetrofitBreedsService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()
        .create(RetrofitBreedsService::class.java)

    override suspend fun getBreeds(): List<Breed> = withContext(Dispatchers.IO) {
        val result = breedsService.getBreedsFlow()
        result.message.keys.map {
            Breed(it)
        }
    }
}