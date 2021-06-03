package com.example.myprohelper.ui.load

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myprohelper.data.repositories.DBRepositories

class LoadViewModelFactory(
    private val repository: DBRepositories
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoadViewModel::class.java)){
            return LoadViewModel(repository) as T
        }
        throw IllegalArgumentException("Unkown View Model class")
    }
}