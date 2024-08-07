package store.baribari.demo.domain.auth.dto

import com.fasterxml.jackson.annotation.JsonInclude
import store.baribari.demo.common.entity.embed.Position
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserInfoResponseDto(
    val id: UUID,
    val email: String,
    val phoneNumber: String?,
    val profileImageUrl: String?,
    val nickname: String,
    val accessToken: String?,
    val position: Position?,
) {
    constructor(userInfoDto: UserInfoDto) : this(
        userInfoDto.id,
        userInfoDto.email,
        userInfoDto.phoneNumber,
        userInfoDto.profileImageUrl,
        userInfoDto.nickname,
        userInfoDto.accessToken,
        userInfoDto.position,
    )
}
