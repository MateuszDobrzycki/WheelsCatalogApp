package com.example.wheelscatalogapp

data class User (
    val uid: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val email: String? = null,
    val favWheel: List<String>? = null,
    val image: String? = null
)
