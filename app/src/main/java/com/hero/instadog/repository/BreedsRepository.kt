package com.hero.instadog.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.hero.instadog.api.BreedsService
import com.hero.instadog.database.dao.BreedDao
import com.hero.instadog.executors.AppExecutors
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.repository.model.Resource
import com.hero.instadog.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class BreedsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val breedDao: BreedDao,
    private val breedsService: BreedsService
) {

    fun loadBreeds(): LiveData<Resource<List<Breed>>> {
        return object :
            NetworkBoundResource<List<Breed>, com.hero.instadog.api.model.BreedListApiResponseData>(
                appExecutors
            ) {
            override fun saveCallResult(item: com.hero.instadog.api.model.BreedListApiResponseData) {
                breedDao.insertBreeds(
                    item.message.keys.map { key ->
                        com.hero.instadog.database.model.Breed(
                            key,
                            null
                        )
                    }
                )
            }

            override fun shouldFetch(data: List<Breed>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() =
                breedDao.load().map { breed -> breed.map { Breed(it.name, it.imageUrl) } }

            override fun createCall() = breedsService.getBreeds()
        }.asLiveData()
    }

    fun loadBreed(name: String): LiveData<Resource<Breed>> {
        return breedDao.loadBreed(name)
            .switchMap { breed -> loadBreedImage(breedName = breed.name) }
    }

    fun loadBreedImage(breedName: String): LiveData<Resource<Breed>> {
        return object :
            NetworkBoundResource<Breed, com.hero.instadog.api.model.RandomBreedImageApiResponse>(
                appExecutors
            ) {
            override fun saveCallResult(item: com.hero.instadog.api.model.RandomBreedImageApiResponse) {
                breedDao.insert(
                    item.message.run {
                        com.hero.instadog.database.model.Breed(
                            breedName,
                            this
                        )
                    }
                )
            }

            override fun shouldFetch(data: Breed?): Boolean {
                return data?.imageUrl == null
            }

            override fun loadFromDb() =
                breedDao.loadBreed(breedName).map { Breed(it.name, it.imageUrl) }

            override fun createCall() = breedsService.getBreedImage(breedName)
        }.asLiveData()
    }
}