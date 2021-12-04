package com.hero.instadog.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hero.instadog.api.retrofit.RetrofitBreedsService
import com.hero.instadog.database.dao.BreedDao
import com.hero.instadog.database.model.Breed
import com.hero.instadog.repository.ApiUtil.successCall
import com.hero.instadog.repository.model.Resource
import com.hero.instadog.util.InstantAppExecutors
import com.hero.instadog.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class BreedsRepositoryTest {

    private lateinit var breedsRepository: BreedsRepository

    private val breedDao = Mockito.mock(BreedDao::class.java)
    private val breedService = Mockito.mock(RetrofitBreedsService::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        breedsRepository = BreedsRepository(InstantAppExecutors(), breedDao, breedService)
    }

    @Test
    fun shouldLoadRepoFromNetworkWithSuccess() {
        val breedName = "australian"
        val expectedBreedToBeSaved = Breed(breedName, null)
        val expectedBreedToBeShow = com.hero.instadog.repository.model.Breed(breedName)

        val dbData = MutableLiveData<List<Breed>>()
        Mockito.`when`(breedDao.load()).thenReturn(dbData)

        val breeds = hashMapOf(breedName to listOf("shepherd"))
        val call = successCall(
            com.hero.instadog.api.retrofit.model.BreedListApiResponseData(
                message = breeds,
                status = "success"
            )
        )
        Mockito.`when`(breedService.getBreeds()).thenReturn(call)

        val loadBreedsLiveDataFromRepository = breedsRepository.loadBreeds()
        Mockito.verify(breedDao).load()
        Mockito.verifyNoMoreInteractions(breedService)


        val observer = mock<Observer<Resource<List<com.hero.instadog.repository.model.Breed>>>>()
        loadBreedsLiveDataFromRepository.observeForever(observer)

        Mockito.verifyNoMoreInteractions(breedService)

        Mockito.verify(observer).onChanged(Resource.loading(null))

        val updatedDbData = MutableLiveData<List<Breed>>()
        Mockito.`when`(breedDao.load()).thenReturn(updatedDbData)

        dbData.postValue(emptyList())
        Mockito.verify(breedService).getBreeds()
        Mockito.verify(breedDao).insertBreeds(listOf(expectedBreedToBeSaved))

        updatedDbData.postValue(listOf(expectedBreedToBeSaved))
        Mockito.verify(observer).onChanged(Resource.success(listOf(expectedBreedToBeShow)))
    }

}