package com.hero.instadog.ui.breedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.repository.BreedsRepository
import com.hero.instadog.repository.model.Resource
import com.hero.instadog.repository.model.Status
import javax.inject.Inject

class BreedListViewModel @Inject constructor(private val breedsRepository: BreedsRepository) : ViewModel() {

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