package com.hero.instadog.ui.breedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.hero.instadog.testing.OpenForTesting
import com.hero.instadog.repository.BreedsRepository
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.repository.model.Resource
import javax.inject.Inject

@OpenForTesting
class BreedListViewModel @Inject constructor(breedsRepository: BreedsRepository) : ViewModel() {

    private val shouldRetry = MutableLiveData(Unit)

    val breeds: LiveData<Resource<List<Breed>>> = shouldRetry.switchMap {
        breedsRepository.loadBreeds()
    }

    fun retry() {
        shouldRetry.value = Unit
    }

    class AbsentLiveData<T : Any?> private constructor() : LiveData<T>() {
        init {
            postValue(null)
        }

        companion object {
            fun <T> create(): LiveData<T> {
                return AbsentLiveData()
            }
        }
    }
}