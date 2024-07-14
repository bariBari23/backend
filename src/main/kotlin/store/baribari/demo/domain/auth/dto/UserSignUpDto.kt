package store.baribari.demo.domain.auth.dto

import store.baribari.demo.domain.cart.domain.Cart
import store.baribari.demo.domain.user.entity.User
import javax.validation.constraints.Pattern

data class UserSignUpDto(
    @field:Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    val email: String,
    val nickname: String,
    val password: String,
    val phoneNumber: String?,
) {
    fun toUser(): User =
        User(
            email = email,
            nickname = nickname,
            password = password,
            userCart = Cart(),
            phoneNumber = phoneNumber,
        )
}
