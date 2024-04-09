package com.example.applicationquizforstudents.domain.models

abstract class User {
    abstract val id: String;
    abstract val name: String;
    abstract val surname: String;
    abstract val email: String;
    abstract val password: String;
    abstract val economy: String;
    abstract val isit: String;
    abstract val electronic_economy: String;
    abstract val accounting: String;
    abstract val role: UserRole;

    class Base(
        override val email: String,
        override val id: String,
        override val name: String = "",
        override val surname: String ="",
        override val role: UserRole=UserRole.USER,
        override val economy: String="",
        override val isit: String="",
        override val electronic_economy: String="",
        override val accounting: String="",
        override val password: String = ""
    ) : User() {
        constructor() : this(email = "", id = "")
    }

    object Empty : User() {
        override val email = "Empty"
        override val password ="Empty"
        override val economy="Empty"
        override val isit="Empty"
        override val electronic_economy="Empty"
        override val accounting="Empty"
        override val role: UserRole = UserRole.USER
        override val id = "Empty"
        override val name="Empty"
        override val surname = "Empty"
    }

}
enum class UserRole {
    USER,
    ADMIN
}

