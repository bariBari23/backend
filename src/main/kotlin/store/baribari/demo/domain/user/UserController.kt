package store.baribari.demo.domain.user

import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.common.dto.ApiResponse
import store.baribari.demo.common.entity.embed.Position
import store.baribari.demo.domain.auth.LoginUser
import store.baribari.demo.domain.user.service.UserService

@Validated
@RestController
@RequestMapping("/api/v1/my")
class UserController(
    private val userService: UserService,
) {
    @PutMapping("/location")
    fun setLocation(
        @LoginUser user: User,
        @RequestBody position: Position,
    ): ApiResponse<Position> {
        val data = userService.setLocation(user.username, position)

        return ApiResponse.success(data)
    }

    @GetMapping("/location")
    fun getLocation(
        @LoginUser user: User,
    ): ApiResponse<Position> {
        val data = userService.getLocation(user.username)

        return ApiResponse.success(data)
    }
}
