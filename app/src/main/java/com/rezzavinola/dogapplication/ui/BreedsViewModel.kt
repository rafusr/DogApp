package com.rezzavinola.dogapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezzavinola.dogapplication.data.DogsRepository
import com.rezzavinola.dogapplication.data.model.entity.DogsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val dogsRepository: DogsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _dogs = MutableLiveData<List<DogsEntity>>()
    val dogs: LiveData<List<DogsEntity>> = _dogs

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun fetchDogs() {
        viewModelScope.launch(ioDispatcher) {
            dogsRepository.getAllDogs()
                .onStart { _isLoading.postValue(true) }
                .onCompletion { _isLoading.postValue(false) }
                .collect { _dogs.postValue(it) }
        }
    }
}