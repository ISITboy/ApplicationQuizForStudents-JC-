package com.example.applicationquizforstudents.domain.models

data class User(
    val id :String="",
    val name :String="",
    val surname:String="",
    val email:String="",
    val password:String="",
    val role:UserRole=UserRole.USER
)


enum class UserRole {
    USER,
    ADMIN
}

