package store.baribari.demo.dto

import store.baribari.demo.common.enums.Role
import store.baribari.demo.model.User
import java.util.*

data class UserInfoDto(
    val id: UUID,
    val email: String,
    val role: Role,
    val accessToken: String?,
    val refreshToken: String?,
) {
    constructor(user: User, accessToken: String? = null, refreshToken: String? = null) : this (
        user.id!!,
        user.email,
        user.role,
        accessToken,
        refreshToken,
    )
}
