package store.baribari.demo.dto

import com.fasterxml.jackson.annotation.JsonInclude
import store.baribari.demo.dto.UserInfoDto
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserInfoResponseDto(
    val id: UUID,
    val email: String,
    val phoneNumber: String?,
    val username: String,
    val accessToken: String?,
) {
    constructor(userInfoDto: UserInfoDto) : this(
        userInfoDto.id,
        userInfoDto.email,
        userInfoDto.phoneNumber,
        userInfoDto.username,
        userInfoDto.accessToken,
    )
}
