package com.hero.instadog.ui

import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.hero.instadog.R
import com.hero.instadog.SingleFragmentActivity
import com.hero.instadog.binding.FragmentBindingAdapters
import com.hero.instadog.repository.model.Breed
import com.hero.instadog.repository.model.Resource
import com.hero.instadog.ui.breedList.BreedListFragment
import com.hero.instadog.ui.breedList.BreedListViewModel
import com.hero.instadog.util.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class BreedsListFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)

    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()

    @Rule
    @JvmField
    val countingAppExecutors = CountingAppExecutorsRule()

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    private val navController = mock<NavController>()

    private val breedsLiveData = MutableLiveData<Resource<List<Breed>>>()


    private lateinit var viewModel: BreedListViewModel

    private lateinit var mockBindingAdapter: FragmentBindingAdapters

    private val repoFragment = BreedListFragment()

    @Before
    fun init() {
        viewModel = Mockito.mock(BreedListViewModel::class.java)

        mockBindingAdapter = Mockito.mock(FragmentBindingAdapters::class.java)

        Mockito.`when`(viewModel.breeds).thenReturn(breedsLiveData)

        repoFragment.appExecutors = countingAppExecutors.appExecutors

        repoFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)

        repoFragment.dataBindingComponent = object : DataBindingComponent {
            override fun getFragmentBindingAdapters(): FragmentBindingAdapters {
                return mockBindingAdapter
            }
        }

        Navigation.setViewNavController(
            activityRule.activity.findViewById<View>(R.id.container),
            navController
        )

        activityRule.activity.setFragment(repoFragment)

        EspressoTestUtil.disableProgressBarAnimations(activityRule)
    }
}