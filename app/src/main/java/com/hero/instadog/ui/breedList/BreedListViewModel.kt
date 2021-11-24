package com.hero.instadog.ui.breedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hero.instadog.testing.OpenForTesting
import com.hero.instadog.repository.BreedsRepository
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.repository.model.Resource
import javax.inject.Inject

@OpenForTesting
class BreedListViewModel @Inject constructor(breedsRepository: BreedsRepository) : ViewModel() {

    val breeds: LiveData<Resource<List<Breed>>> = breedsRepository.loadBreeds()

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