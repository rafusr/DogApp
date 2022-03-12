package com.rezzavinola.dogapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezzavinola.dogapplication.data.DogsRepository
import com.rezzavinola.dogapplication.data.model.entity.DogsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreedsViewModel(private val repository: DogsRepository) : ViewModel() {

    private val _dogs = MutableLiveData<List<DogsEntity>>()
    val dogs: LiveData<List<DogsEntity>> = _dogs

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun fetchDogs() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAllDogs()
            _dogs.postValue(result)
            withContext(Dispatchers.Main) {
                _isLoading.value = false
            }
        }
    }
}