package com.hero.instadog.ui.breedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hero.instadog.repository.FlowBreedsRepository
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.testing.OpenForTesting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OpenForTesting
class BreedListViewModel @Inject constructor(val breedsRepository: FlowBreedsRepository) :
    ViewModel() {

    private val _loadBreeds = MutableStateFlow(Unit)

    init {
        _loadBreeds.mapLatest {
            viewModelScope.launch(Dispatchers.IO) {
                breedsRepository.fetchBreeds()
            }
        }.launchIn(viewModelScope)
    }

    fun loadBreeds() {
        _loadBreeds.value = Unit
    }

    val breeds: LiveData<List<Breed>> = _loadBreeds.flatMapLatest { _ ->
        breedsRepository.breeds
    }.asLiveData()
}