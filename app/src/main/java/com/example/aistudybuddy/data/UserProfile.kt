package com.example.aistudybuddy.data

data class UserProfile(
    val id: String,
    val name: String? = null,
    val email: String? = null,
    val avatarUrl: String? = null
)
