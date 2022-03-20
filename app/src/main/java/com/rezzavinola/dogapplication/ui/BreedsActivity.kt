package com.rezzavinola.dogapplication.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.rezzavinola.dogapplication.adapter.BreedsAdapter
import com.rezzavinola.dogapplication.databinding.ActivityBreedsBinding
import com.rezzavinola.dogapplication.dialog.CustomLoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreedsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedsBinding
    private lateinit var customLoading: CustomLoadingDialog
    private val viewModel by viewModels<BreedsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customLoading = CustomLoadingDialog(this)

        binding.btnRefresh.setOnClickListener { setupObserver() }
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.fetchDogs()
        }

        viewModel.isLoading.observe(this) {
            if (it) customLoading.show() else customLoading.dismiss()
        }

        viewModel.dogs.observe(this) {
            binding.recyclerDogs.apply {
                layoutManager = GridLayoutManager(this@BreedsActivity, 3)
                adapter = BreedsAdapter(it)
            }
        }
    }
}