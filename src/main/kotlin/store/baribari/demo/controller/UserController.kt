package store.baribari.demo.controller

import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.model.embed.Position
import store.baribari.demo.service.UserService


@Validated
@RestController
@RequestMapping("/api/v1/my")
class UserController(
    private val userService: UserService,
) {

    @PutMapping("/location")
    fun setLocation(
        @LoginUser user: User,
        @RequestBody position : Position,
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