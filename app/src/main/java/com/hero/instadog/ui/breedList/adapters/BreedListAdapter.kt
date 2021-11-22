package com.hero.instadog.ui.breedList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.hero.instadog.R
import com.hero.instadog.databinding.BreedItemBinding
import com.hero.instadog.executors.AppExecutors
import com.hero.instadog.ui.breedList.repository.model.Breed

class BreedListAdapter (
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val repoClickCallback: ((Breed) -> Unit)?
) : DataBoundListAdapter<Breed, BreedItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Breed>() {
        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
            return oldItem.name == newItem.name && oldItem.imageUrl == newItem.imageUrl
        }
    }
) {

    override fun createBinding(parent: ViewGroup): BreedItemBinding {
        val binding = DataBindingUtil.inflate<BreedItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.breed_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.breed?.let {
                repoClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: BreedItemBinding, breed: Breed) {
        binding.breed = breed
    }
}