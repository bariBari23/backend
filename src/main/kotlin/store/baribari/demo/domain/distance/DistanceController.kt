package store.baribari.demo.domain.distance

import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.common.dto.ApiResponse
import store.baribari.demo.domain.auth.LoginUser
import store.baribari.demo.domain.distance.dto.AllDistanceResponseDto
import store.baribari.demo.domain.distance.dto.DistanceResponseDto
import store.baribari.demo.domain.distance.service.LocationService
import javax.validation.constraints.Positive

@Validated
@RestController
@RequestMapping("/api/v1/distance")
class DistanceController(
    private val locationService: LocationService,
) {
    @GetMapping("/{storeId}")
    fun calOneDistance(
        @LoginUser username: User,
        @PathVariable @Positive storeId: Long,
    ): ApiResponse<DistanceResponseDto> {
        val data = locationService.calOneDistance(username.username, storeId)

        return ApiResponse.success(data)
    }

    @GetMapping("")
    fun allDistance(
        @LoginUser username: User,
    ): ApiResponse<AllDistanceResponseDto> {
        val data = locationService.calAllDistance(username.username)

        return ApiResponse.success(data)
    }
}
