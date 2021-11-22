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
        value = Resource(Status.SUCCESS, listOf(Breed("Chiauau")), "oee!")
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