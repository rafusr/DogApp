package com.rezzavinola.dogapplication.ui

import com.rezzavinola.dogapplication.model.network.search.SearchResponse
import com.rezzavinola.dogapplication.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BreedsPresenterImp(
    private val view: BreedsView,
    private val globalScope: GlobalScope,
) : BreedsPresenter {
    override fun getDog() {
        view.showLoading()
        globalScope.launch(Dispatchers.IO) {
            ApiClient.instance.searchImages(size = "med", limit = 20)
                .enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {
                        if (response.code() == 200) {
                            val dogs = response.body()!!
                            if (dogs.isEmpty()) {
                                view.showError("Tidak ada anjing di server")
                            } else {
                                val dog = response.body()!!
                                globalScope.launch {
                                    view.showDogs(dog)
                                }
                            }
                            view.hideLoading()
                        } else {
                            view.showError("Masalah koneksi ke server ${response.code()}")
                            view.hideLoading()
                        }
                    }

                    override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                        view.showError(t.message.toString())
                        view.hideLoading()
                    }
                })
        }
    }

}