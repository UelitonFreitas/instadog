package com.hero.instadog.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.hero.instadog.api.BreedsService
import com.hero.instadog.database.dao.BreedDao
import com.hero.instadog.executors.AppExecutors
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.repository.model.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BreedsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val breedDao: BreedDao,
    private val breedsService: BreedsService
) {

    fun loadBreeds(): LiveData<Resource<List<Breed>>> {
        return object :
            NetworkBoundResource<List<Breed>, List<com.hero.instadog.api.model.Breed>>(appExecutors) {
            override fun saveCallResult(item: List<com.hero.instadog.api.model.Breed>) {
                breedDao.insertBreeds(item.map {
                    com.hero.instadog.database.model.Breed(
                        it.name,
                        it.imageUrl
                    )
                })
            }

            override fun shouldFetch(data: List<Breed>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() =
                breedDao.load().map { breed -> breed.map { Breed(it.name, it.imageUrl) } }

            override fun createCall() = breedsService.getBreeds()
        }.asLiveData()
    }
}