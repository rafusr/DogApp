package com.rezzavinola.dogapplication.data

import android.content.Context
import android.util.Log
import com.rezzavinola.dogapplication.data.local.DogsDatabase
import com.rezzavinola.dogapplication.data.model.entity.DogsEntity
import com.rezzavinola.dogapplication.data.remote.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogsRepository(context: Context) {
    private val retrofit = ApiClient.instance
    private val database = DogsDatabase.instance(context)

    suspend fun getAllDogs(): List<DogsEntity> {
        val result = ArrayList<DogsEntity>()
        withContext(Dispatchers.IO) {
            try {
                val searchImages = retrofit.searchImages(limit = 30)
                if (searchImages.isSuccessful) {
                    val dogsResponse = searchImages.body()!!
                    val listDogs = ArrayList<DogsEntity>()

                    dogsResponse.forEach {
                        listDogs.add(
                            DogsEntity(
                                imageUrl = it.url
                            )
                        )
                    }

                    database.dogsDao().insertAllDogs(listDogs)
                }

                val dogsFromDb = database.dogsDao().getAllDogs()
                result.addAll(dogsFromDb)
            } catch (e: Exception) {
                Log.d("Exception", e.message.toString())
            }
        }
        return result
    }
}