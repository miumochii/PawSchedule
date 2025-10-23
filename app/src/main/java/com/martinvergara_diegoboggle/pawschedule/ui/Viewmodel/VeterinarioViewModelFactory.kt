package com.martinvergara_diegoboggle.pawschedule.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VeterinarioViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VeterinarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VeterinarioViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}