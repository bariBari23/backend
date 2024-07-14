package store.baribari.demo.domain.user.service

import store.baribari.demo.common.entity.embed.Position

interface UserService {
    fun setLocation(
        userEmail: String,
        position: Position,
    ): Position

    fun getLocation(userEmail: String): Position
}
