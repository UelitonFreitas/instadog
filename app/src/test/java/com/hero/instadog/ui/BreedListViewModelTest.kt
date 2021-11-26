package com.hero.instadog.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.hero.instadog.repository.BreedsRepository
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.repository.model.Resource
import com.hero.instadog.ui.breedList.BreedListViewModel
import com.hero.instadog.util.TestUtil.createBreed
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class BreedListViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val breedsRepository = Mockito.mock(BreedsRepository::class.java)
    private lateinit var breedsViewModel: BreedListViewModel


    @Before
    fun setUp() {
        breedsViewModel = BreedListViewModel(breedsRepository)
    }

    @Test
    fun shouldNotHaveNullLiveData() {
        MatcherAssert.assertThat(breedsViewModel.breeds, CoreMatchers.notNullValue())
    }

    @Test
    fun shouldStartWithoutBreeds() {
        MatcherAssert.assertThat(breedsViewModel.breeds, CoreMatchers.notNullValue())

        Mockito.verify(breedsRepository, Mockito.never()).loadBreeds()
    }

    @Test
    fun shouldNotLoadWithoutObservers() {
        breedsViewModel.loadBreeds()
        Mockito.verify(breedsRepository, Mockito.never()).loadBreeds()
    }

    @Test
    fun shouldLoadBreeds() {

        breedsViewModel.loadBreeds()

        val data = Resource.success(listOf(createBreed("a", "a")))

        val breedsLiveData = MutableLiveData<Resource<List<Breed>>>(data)
        `when`(breedsRepository.loadBreeds()).thenReturn(breedsLiveData)

        Mockito.verifyNoMoreInteractions(breedsRepository)

        breedsViewModel.breeds.observeForever { }

        assertEquals(data, breedsViewModel.breeds.value)
    }
}
