package com.hero.instadog.repository


import com.hero.instadog.database.dao.BreedDao
import com.hero.instadog.repository.model.Breed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlowBreedsRepository @Inject constructor(val breedDao: BreedDao) {
    fun getBreeds(): Flow<List<Breed>> {
        return breedDao.getBreedsWithFlow().map { breedList ->
            breedList.map { Breed(it.name, it.imageUrl) }
        }
    }
}