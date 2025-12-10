package com.agritask.data

import com.agritask.domain.models.Location
import com.agritask.domain.models.User

object DataRepository {
    private var userInfo: User? = null
    private var locations: List<Location> = emptyList()

    fun getUser(): User? = userInfo
    fun getLocations(): List<Location> = locations

    suspend fun loadFromServer() {
        userInfo = User(
            id = 1,
            username = "demo.user",
            email = "demo@example.com",
            firstName = "Demo",
            lastName = "User",
            group = "Test Grower"
        )
    }
}