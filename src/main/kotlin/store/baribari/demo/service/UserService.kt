package store.baribari.demo.service

import store.baribari.demo.model.embed.Position

interface UserService {

    fun setLocation(
        userEmail: String,
        position: Position,
    ) : Position

    fun getLocation(
        userEmail: String,
    ): Position
}
