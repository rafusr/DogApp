package com.rezzavinola.dogapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rezzavinola.dogapplication.databinding.ItemDogBinding
import com.rezzavinola.dogapplication.model.network.search.SearchResponseItem

class BreedsAdapter(
    private var dogs: List<SearchResponseItem>,
) : RecyclerView.Adapter<BreedsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDogBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsAdapter.ViewHolder {
        val view = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dogs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dog = dogs[position]
        holder.binding.root.context.let {
            Glide.with(holder.binding.imgDog.context)
                .load(dog.url)
                .into(holder.binding.imgDog)
        }
    }
}