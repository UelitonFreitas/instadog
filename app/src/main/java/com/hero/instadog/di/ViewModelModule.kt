package com.hero.instadog.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hero.instadog.ui.breedList.BreedListViewModel
import com.hero.instadog.viewmodel.BreedListViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BreedListViewModel::class)
    abstract fun bindBreedListViewModel(breedListViewModel: BreedListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: BreedListViewModelFactory): ViewModelProvider.Factory
}