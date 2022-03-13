package com.rezzavinola.dogapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rezzavinola.dogapplication.R
import com.rezzavinola.dogapplication.data.model.entity.DogsEntity
import com.rezzavinola.dogapplication.databinding.ItemDogBinding

class BreedsAdapter(
    private var dogs: List<DogsEntity>,
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
            Glide.with(it)
                .load(dog.imageUrl)
                .placeholder(R.drawable.img_dog)
                .into(holder.binding.imgDog)
        }
    }
}