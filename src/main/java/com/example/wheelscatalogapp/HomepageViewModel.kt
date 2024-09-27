package com.example.wheelscatalogapp

import androidx.lifecycle.ViewModel

class HomepageViewModel : ViewModel() {
    private val repository = FirebaseRepository()
    val wheel = repository.getWheel()
    fun addFavWheel(wheel: Wheel){
        repository.addFavWheel(wheel)
    }
}