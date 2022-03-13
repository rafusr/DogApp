package com.rezzavinola.dogapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.rezzavinola.dogapplication.ViewModelFactory
import com.rezzavinola.dogapplication.adapter.BreedsAdapter
import com.rezzavinola.dogapplication.data.DogsRepository
import com.rezzavinola.dogapplication.databinding.ActivityBreedsBinding
import com.rezzavinola.dogapplication.dialog.CustomLoadingDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BreedsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedsBinding
    private lateinit var customLoading: CustomLoadingDialog
    private lateinit var repository: DogsRepository
    private lateinit var viewModel: BreedsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customLoading = CustomLoadingDialog(this)
        repository = DogsRepository(this)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository)
        )[BreedsViewModel::class.java]

        binding.btnRefresh.setOnClickListener {
            setupViewModel()
        }

        setupViewModel()
    }

    private fun setupViewModel() {
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

    override fun onDestroy() {
        super.onDestroy()
        repository.destroyInstanceDatabase
    }
}