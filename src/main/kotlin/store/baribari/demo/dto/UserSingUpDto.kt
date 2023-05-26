package store.baribari.demo.dto

import store.baribari.demo.auth.ProviderType
import store.baribari.demo.model.User

data class UserSignUpDto(
    val email: String,
    val username: String,
    val password: String,
) {
    fun toUser(): User {
        return User(
            email = email,
            password = password,
            providerType = ProviderType.LOCAL,
        )
    }
}
