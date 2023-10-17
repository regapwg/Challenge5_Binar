package com.example.challenge2_binar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge2_binar.produk.MenuList

class HomeViewModel : ViewModel() {
    val isGrid = MutableLiveData<Boolean>().apply {
        value = true
    }
}