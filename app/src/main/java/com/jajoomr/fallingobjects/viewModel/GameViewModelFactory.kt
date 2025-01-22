package com.jajoomr.fallingobjects.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameViewModelFactory (
    private val screenHeight: Float,
    private val screenWidth: Float
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(screenHeight, screenWidth) as T
        } else {
            throw IllegalArgumentException("View Model Not Found")
        }
    }
}