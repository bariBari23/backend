package store.baribari.demo.domain.auth.dto

data class TokenDto(
    val accessToken: String,
    val refreshToken: String?,
)
