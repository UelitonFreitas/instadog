package com.hero.instadog.repository


import com.hero.instadog.api.BreedsService
import com.hero.instadog.database.dao.BreedDao
import com.hero.instadog.repository.model.Breed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlowBreedsRepository @Inject constructor(
    val breedDao: BreedDao,
    private val breedsService: BreedsService
) {

    private var breedsCache = CacheOnSuccess(onErrorFallback = { listOf<Breed>() }) {
//        fetchBreeds()
    }

    suspend fun fetchBreeds(forceRefresh: Boolean = true) {
        val breeds = breedsService.getBreeds()

        breedDao.insertBreeds(breeds.map {
            com.hero.instadog.database.model.Breed(
                it.name,
                it.imageUrl
            )
        })

    }

    val breeds: Flow<List<Breed>>
        get() = breedDao.getBreedsWithFlow().map { breedList ->
            breedList.map { breedFromRepository ->
                breedFromRepository.takeIf { it.imageUrl == null }?.let {
                    breedsCache.getOrAwait()
                    Breed(it.name, it.imageUrl)
                } ?: breedFromRepository.let {
                    Breed(it.name, it.imageUrl)
                }
            }
        }

}