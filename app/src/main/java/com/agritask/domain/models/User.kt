package com.agritask.domain.models

class User(
    val id: Long,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val group: String,
) {
    val fullName: String
        get() = "$firstName $lastName"

    init {
        require(username.isNotBlank()) { "Username cannot be empty" }
        require(email.contains("@")) { "Invalid email format: $email" }
    }
}