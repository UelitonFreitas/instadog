package com.hero.instadog.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.hero.instadog.api.retrofit.RetrofitBreedsService
import com.hero.instadog.database.dao.BreedDao
import com.hero.instadog.database.model.BreedWithSubBreeds
import com.hero.instadog.executors.AppExecutors
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.repository.model.Resource
import com.hero.instadog.repository.model.SubBreed
import com.hero.instadog.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class BreedsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val breedDao: BreedDao,
    private val retrofitBreedsService: RetrofitBreedsService
) {

//    fun loadBreeds(): LiveData<Resource<List<Breed>>> {
//        return object :
//            NetworkBoundResource<List<Breed>, com.hero.instadog.api.retrofit.model.BreedListApiResponseData>(
//                appExecutors
//            ) {
//            override fun saveCallResult(item: com.hero.instadog.api.retrofit.model.BreedListApiResponseData) {
//                breedDao.insertBreeds(
//                    item.message.keys.map { key ->
//                        com.hero.instadog.database.model.Breed(
//                            key,
//                            null
//                        )
//                    }
//                )
//
//                item.message.keys.map { breedName ->
//                    val subBreeds = item.message[breedName]?.map { subBreedName ->
//                        com.hero.instadog.database.model.SubBreed(
//                            subBreedName,
//                            null,
//                            breedName = breedName
//                        )
//                    }
//
//                    subBreeds?.let {
//                        breedDao.insertSubBreeds(it)
//                    }
//
//                }
//            }
//
//            override fun shouldFetch(data: List<Breed>?): Boolean {
//                return data == null || data.isEmpty()
//            }
//
//            override fun loadFromDb() =
//                breedDao.load().map { breed -> breed.map { Breed(it.name, it.imageUrl) } }
//
//            override fun createCall() = retrofitBreedsService.getBreeds()
//        }.asLiveData()
//    }

//    fun loadBreed(name: String): LiveData<Resource<Breed>> {
//        return breedDao.getBreedWithSubBreeds(name)
//            .switchMap { breedWithSubBreed -> loadBreedImage(breedWithSubBreed) }
//    }

//    fun loadBreedImage(breedWithSubBreed: BreedWithSubBreeds): LiveData<Resource<Breed>> {
//        return object :
//            NetworkBoundResource<Breed, com.hero.instadog.api.retrofit.model.RandomBreedImageApiResponse>(
//                appExecutors
//            ) {
//            override fun saveCallResult(item: com.hero.instadog.api.retrofit.model.RandomBreedImageApiResponse) {
//                breedDao.insert(
//                    item.message.run {
//                        com.hero.instadog.database.model.Breed(
//                            breedWithSubBreed.breed.name,
//                            this
//                        )
//                    }
//                )
//            }
//
//            override fun shouldFetch(data: Breed?): Boolean {
//                return data?.imageUrl == null
//            }
//
//            override fun loadFromDb() =
//                breedDao.loadBreed(breedWithSubBreed.breed.name).map { it -> Breed(it.name, it.imageUrl, breedWithSubBreed.subBreeds.map { SubBreed(it.name) }) }
//
//            override fun createCall() = retrofitBreedsService.getBreedImage(breedWithSubBreed.breed.name)
//        }.asLiveData()
//    }
}