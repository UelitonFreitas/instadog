package com.hero.instadog.ui.breedDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.hero.instadog.repository.BreedsRepository
import javax.inject.Inject

class BreedDetailViewModel @Inject constructor(breedsRepository: BreedsRepository) : ViewModel() {

    private val _breedName = MutableLiveData<String>()

    val breedFromRepo = _breedName.switchMap { breedName ->
        breedsRepository.loadBreed(breedName)
    }

    fun loadBreed(name: String) {
        _breedName.value = name
    }
}