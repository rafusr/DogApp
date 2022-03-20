package com.rezzavinola.dogapplication.data

import android.util.Log
import com.rezzavinola.dogapplication.data.local.DogsDao
import com.rezzavinola.dogapplication.data.model.entity.DogsEntity
import com.rezzavinola.dogapplication.data.remote.ApiService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DogsRepository @Inject constructor(
    private val apiService: ApiService,
    private val dogsDao: DogsDao,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getAllDogs() = flow {
        val searchImages = apiService.searchImages(limit = 20)
        searchImages.suspendOnSuccess {
            val result = ArrayList<DogsEntity>()
            data.forEach { item ->
                result.add(DogsEntity(imageUrl = item.url))
            }
            dogsDao.insertAllDogs(result)

        }.onError { Log.i("On Error", message()) }
            .onException { Log.i("On Exception", message.toString()) }

        val localDogs = dogsDao.getAllDogs()
        emit(localDogs)

    }.flowOn(ioDispatcher)
}