package store.baribari.demo.controller

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
        username: String,
        latitude: Double,
        longitude: Double,
    ): ApiResponse<Position> {
        val data = userService.setLocation(username, latitude, longitude)

        return ApiResponse.success(data)
    }


}