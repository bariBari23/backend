package store.baribari.demo.dto

import store.baribari.demo.model.User
import store.baribari.demo.model.cart.Cart

data class UserSignUpDto(
    val email: String,
    val username: String,
    val password: String,
) {
    fun toUser(): User {
        return User(
            email = email,
            password = password,
            userCart = Cart(),
        )
    }
}
