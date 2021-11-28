package com.hero.instadog.api

import androidx.lifecycle.LiveData
import com.hero.instadog.api.model.RandomBreedImageApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BreedsService {
    @GET("breeds/list/all")
    fun getBreeds(): LiveData<ApiResponse<com.hero.instadog.api.model.BreedListApiResponseData>>

    @GET("breed/{breedName}/images/random")
    fun getBreedImage(@Path("breedName") breedName : String) : LiveData<ApiResponse<RandomBreedImageApiResponse>>
}