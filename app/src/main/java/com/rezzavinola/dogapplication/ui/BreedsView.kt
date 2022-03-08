package com.rezzavinola.dogapplication.ui

import com.rezzavinola.dogapplication.BaseView
import com.rezzavinola.dogapplication.model.network.search.SearchResponse

interface BreedsView: BaseView {

    fun showDogs(dogs: SearchResponse)

    fun refreshDogs()

    fun showError(message: String)
}