package com.rezzavinola.dogapplication.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.rezzavinola.dogapplication.adapter.BreedsAdapter
import com.rezzavinola.dogapplication.databinding.ActivityBreedsBinding
import com.rezzavinola.dogapplication.dialog.CustomLoadingDialog
import com.rezzavinola.dogapplication.model.network.search.SearchResponse
import kotlinx.coroutines.GlobalScope

class BreedsActivity : AppCompatActivity(), BreedsView {

    private lateinit var binding: ActivityBreedsBinding
    private lateinit var customLoading: CustomLoadingDialog
    private var presenter: BreedsPresenterImp? = null
    private var breedsAdapter: BreedsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customLoading = CustomLoadingDialog(this)
        presenter = BreedsPresenterImp(this, GlobalScope)

        refreshDogs()

        binding.btnRefresh.setOnClickListener {
            refreshDogs()
        }

    }

    override fun showDogs(dogs: SearchResponse) {
        breedsAdapter = BreedsAdapter(dogs)
        runOnUiThread {
            binding.recyclerDogs.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = breedsAdapter
            }
        }
    }

    override fun refreshDogs() {
        presenter?.getDog()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        customLoading.show()
    }

    override fun hideLoading() {
        customLoading.dismiss()
    }
}