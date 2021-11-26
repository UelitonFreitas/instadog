package com.hero.instadog.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET

interface BreedsService {
    @GET("breeds/list/all")
    fun getBreeds(): LiveData<ApiResponse<com.hero.instadog.api.model.ApiResponseData>>
}