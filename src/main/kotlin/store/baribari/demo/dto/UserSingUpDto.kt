package store.baribari.demo.dto

import store.baribari.demo.model.User
import store.baribari.demo.model.cart.Cart
import javax.validation.constraints.Email

data class UserSignUpDto(

    val email: String,
    val username: String,
    val password: String,
    val phoneNumber: String?,
) {
    fun toUser(): User {
        return User(
            email = email,
            username = username,
            password = password,
            userCart = Cart(),
            phoneNumber = phoneNumber,
        )
    }
}
