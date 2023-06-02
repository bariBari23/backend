package store.baribari.demo.controller

import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.dto.ShowLikeResponseDto
import store.baribari.demo.dto.StoreInfoResponseDto
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.service.StoreService
import java.security.Principal
import javax.validation.constraints.Positive

@Validated
@RestController
@RequestMapping("/api/v1/store")
class StoreController(
    private val storeService: StoreService,
) {
    // 가게 정보
    @GetMapping("/{storeId}")
    fun storeInfo(
        principal: Principal?,
        @PathVariable @Positive storeId: Long,
    ): ApiResponse<StoreInfoResponseDto> {
        // login한 유저면 like 여부도 같이 보내준다.
        val data = storeService.storeInfo(username = principal?.name, storeId = storeId)

        return ApiResponse.success(data)
    }

    @PostMapping("/{storeId}/like")
    fun storeLike(
        @LoginUser loginUser: User,
    ): ApiResponse<Long> {
        val data = storeService.storeLike(loginUser.username, 1L)

        return ApiResponse.success(data)
    }

    @DeleteMapping("/{storeId}/like")
    fun storeLikeCancel(
        @LoginUser loginUser: User,
    ): ApiResponse<Long> {
        val data = storeService.storeLikeCancel(loginUser.username, 1L)

        return ApiResponse.success(data)
    }

    @GetMapping("/like")
    fun showLike(
        @LoginUser loginUser: User,
    ): ApiResponse<ShowLikeResponseDto> {
        val data = storeService.showLike(loginUser.username)

        return ApiResponse.success(data)
    }

}