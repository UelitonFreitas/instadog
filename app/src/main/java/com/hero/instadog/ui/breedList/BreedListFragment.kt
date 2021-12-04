package com.hero.instadog.ui.breedList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hero.instadog.R
import com.hero.instadog.binding.AutoClearedValue
import com.hero.instadog.binding.FragmentDataBindingComponent
import com.hero.instadog.databinding.BreedListFragmentBinding
import com.hero.instadog.di.Injectable
import com.hero.instadog.executors.AppExecutors
import com.hero.instadog.ui.breedList.adapters.BreedListAdapter
import javax.inject.Inject

class BreedListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<BreedListFragmentBinding>()

    var adapter by autoCleared<BreedListAdapter>()

    val breedsViewModel: BreedListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.breed_list_fragment,
            container,
            false,
            dataBindingComponent
        )

        binding.retryCallback = object : RetryButtonCallback {
            override fun retry() {
                breedsViewModel.loadBreeds()
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

        initRecyclerView()

        val breedsListAdapter = BreedListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ) { breed ->

            findNavController().navigate(
                BreedListFragmentDirections.breedDetail(breedName = breed.name)
            )

        }
        binding.breeds = breedsViewModel.breeds
        binding.recyclerViewBreedList.adapter = breedsListAdapter
        adapter = breedsListAdapter
    }

    private fun initRecyclerView() {

        binding.breeds = breedsViewModel.breeds
        breedsViewModel.breeds.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result)
        })
    }

    interface RetryButtonCallback {
        fun retry()
    }

    override fun onResume() {
        super.onResume()
        breedsViewModel.loadBreeds()
    }
}

fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)