package com.hero.instadog.api

import androidx.lifecycle.LiveData
import com.hero.instadog.api.model.Root
import retrofit2.http.GET

interface BreedsService {
    @GET("breeds/list/all")
    fun getBreeds(): LiveData<ApiResponse<Root>>
}