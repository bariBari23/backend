package store.baribari.demo.service

import store.baribari.demo.model.embed.Position

interface UserService {

    fun setLocation(
        userEmail: String,
        latitude: Double,
        longitude: Double,
    ) : Position
}
