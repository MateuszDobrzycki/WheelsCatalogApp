package com.example.wheelscatalogapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class ProfileViewModel : ViewModel(){
    private val repository = FirebaseRepository()
    val user = repository.getUserData()
    val favWheel = user.switchMap {
        repository.getFavWheel(it.favWheel)
    }
    fun removeFavWheel(wheel: Wheel){
        repository.removeFavWheel(wheel)
    }
    fun editProfileDane(map: Map<String, String>){
        repository.editProfileDane(map)
    }
}