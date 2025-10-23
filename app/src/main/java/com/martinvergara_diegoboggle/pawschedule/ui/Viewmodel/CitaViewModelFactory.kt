package com.martinvergara_diegoboggle.pawschedule.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CitaViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CitaViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}