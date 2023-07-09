package store.baribari.demo.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserInfoResponseDto(
    val id: UUID,
    val email: String,
    val phoneNumber: String?,
    val nickname: String,
    val accessToken: String?,
) {
    constructor(userInfoDto: UserInfoDto) : this(
        userInfoDto.id,
        userInfoDto.email,
        userInfoDto.phoneNumber,
        userInfoDto.nickname,
        userInfoDto.accessToken,
    )
}
