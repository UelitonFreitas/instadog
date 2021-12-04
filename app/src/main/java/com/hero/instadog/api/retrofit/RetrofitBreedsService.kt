package com.hero.instadog.api.retrofit

import com.hero.instadog.api.retrofit.model.BreedListApiResponseData
import retrofit2.http.GET

interface RetrofitBreedsService {
//    @GET("breeds/list/all")
//    fun getBreeds(): LiveData<ApiResponse<BreedListApiResponseData>>
//
//    @GET("breed/{breedName}/images/random")
//    fun getBreedImage(@Path("breedName") breedName : String) : LiveData<ApiResponse<RandomBreedImageApiResponse>>

    @GET("breeds/list/all")
    suspend fun getBreedsFlow(): BreedListApiResponseData
}