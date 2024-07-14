package store.baribari.demo.domain.auth.dto

import store.baribari.demo.common.entity.embed.Position
import store.baribari.demo.common.enums.Role
import store.baribari.demo.domain.user.entity.User
import java.util.UUID

data class UserInfoDto(
    val id: UUID,
    val email: String,
    val role: Role,
    val phoneNumber: String?,
    val nickname: String,
    val profileImageUrl: String?,
    val position: Position?,
    val accessToken: String?,
    val refreshToken: String?,
) {
    constructor(user: User, accessToken: String? = null, refreshToken: String? = null) : this (
        user.id!!,
        user.email,
        user.role,
        user.phoneNumber,
        user.nickname,
        user.profileImageUrl,
        user.position,
        accessToken,
        refreshToken,
    )
}
