package com.hero.instadog.ui.breedDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.hero.instadog.R
import com.hero.instadog.binding.FragmentDataBindingComponent
import com.hero.instadog.databinding.BreedDetailFragmentBinding
import com.hero.instadog.di.Injectable
import com.hero.instadog.executors.AppExecutors
import com.hero.instadog.ui.breedList.autoCleared
import javax.inject.Inject

class BreedDetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<BreedDetailFragmentBinding>()

    private val breedDetailViewModel: BreedDetailViewModel by viewModels {
        viewModelFactory
    }

    private val params by navArgs<BreedDetailFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.breed_detail_fragment,
            container,
            false,
            dataBindingComponent
        )


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        breedDetailViewModel.loadBreed(params.breedName)
        binding.breed = breedDetailViewModel.breeds

        binding.lifecycleOwner = viewLifecycleOwner
    }

}