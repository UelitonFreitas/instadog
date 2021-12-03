package com.hero.instadog.ui.breedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hero.instadog.repository.FlowBreedsRepository
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.testing.OpenForTesting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OpenForTesting
class BreedListViewModel @Inject constructor(val breedsRepository: FlowBreedsRepository) : ViewModel() {

    private val _loadBreeds = MutableStateFlow(Unit)

//    val breeds: LiveData<Resource<List<Breed>>> = MutableLiveData()

    fun loadBreeds() {
        _loadBreeds.value = Unit
    }

    val breeds: LiveData<List<Breed>> = _loadBreeds.flatMapLatest { _ ->
        breedsRepository.getBreeds()
    }.asLiveData()
}