package store.baribari.demo.dto

import store.baribari.demo.model.User
import store.baribari.demo.model.cart.Cart
import javax.validation.constraints.Pattern

data class UserSignUpDto(
    @field: Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
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
