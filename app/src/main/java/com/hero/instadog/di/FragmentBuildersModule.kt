package com.hero.instadog.di;

import com.hero.instadog.ui.breedDetail.BreedDetailFragment
import com.hero.instadog.ui.breedList.BreedListFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeBreedDetailFragment(): BreedDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeBreedListFragment(): BreedListFragment
}
