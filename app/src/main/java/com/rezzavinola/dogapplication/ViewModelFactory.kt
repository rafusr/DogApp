package com.rezzavinola.dogapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rezzavinola.dogapplication.data.DogsRepository

class ViewModelFactory(
    private val repository: DogsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return modelClass.getDeclaredConstructor(
                DogsRepository::class.java
            ).newInstance(repository) as T
        } catch (e: Exception) {
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}