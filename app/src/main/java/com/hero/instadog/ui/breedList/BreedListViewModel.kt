package com.hero.instadog.ui.breedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.hero.instadog.repository.BreedsRepository
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.repository.model.Resource
import com.hero.instadog.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class BreedListViewModel @Inject constructor(breedsRepository: BreedsRepository) : ViewModel() {

    private val _loadBreeds = MutableLiveData<Unit>()

    val breeds: LiveData<Resource<List<Breed>>> = _loadBreeds.switchMap {
        breedsRepository.loadBreeds()
    }

    fun loadBreeds() {
        _loadBreeds.value = Unit
    }
}