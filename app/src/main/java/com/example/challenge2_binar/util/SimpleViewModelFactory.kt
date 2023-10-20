package com.example.challenge2_binar.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge2_binar.api.APIService
import com.example.challenge2_binar.viewModel.SimpleViewModel

class SimpleViewModelFactory (val apiService: APIService): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SimpleViewModel::class.java)) {
            return SimpleViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}