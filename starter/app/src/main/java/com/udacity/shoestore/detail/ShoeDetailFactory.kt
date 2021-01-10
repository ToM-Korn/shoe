package com.udacity.shoestore.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShoeDetailFactory (private val shoeId: Int, private val create: Boolean) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoeDetailViewModel::class.java)){
            return ShoeDetailViewModel(shoeId, create) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

