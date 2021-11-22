package com.hero.instadog.ui.breedList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hero.instadog.ui.breedList.repository.model.Breed
import com.hero.instadog.ui.breedList.repository.model.Resource
import com.hero.instadog.ui.breedList.repository.model.Status
import javax.inject.Inject

class BreedListViewModel @Inject constructor() : ViewModel() {

    val breeds: LiveData<Resource<List<Breed>>> = MutableLiveData<Resource<List<Breed>>>().apply{
        value = Resource(Status.SUCCESS, listOf(
            Breed("affenpinscher", "https://images.dog.ceo/breeds/affenpinscher/n02110627_6842.jpg"),
            Breed("hound", "https://images.dog.ceo/breeds/hound-afghan/n02088094_6485.jpg"),
            Breed("airedale", "https://images.dog.ceo/breeds/airedale/n02096051_1206.jpg")
        ), "oee!")
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