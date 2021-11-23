package com.hero.instadog.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiBuilder @Inject constructor() {
    fun buildBreedService(
        baseUrl: String,
        loggingLevel: HttpLoggingInterceptor.Level
    ): BreedsService {
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = loggingLevel })
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(client)
            .build()
            .create(BreedsService::class.java)
    }
}