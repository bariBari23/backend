package store.baribari.demo.domain.store

import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.common.dto.ApiResponse
import store.baribari.demo.domain.auth.LoginUser
import store.baribari.demo.domain.store.dto.ShowLikeResponseDto
import store.baribari.demo.domain.store.dto.StoreInfoResponseDto
import store.baribari.demo.domain.store.service.StoreService
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
        // 자기 상점인지 확인하는 로직도 나중에 넣어준다.
        val data = storeService.storeInfo(userEmail = principal?.name, storeId = storeId)

        return ApiResponse.success(data)
    }

    @PostMapping("/like/{storeId}")
    fun storeLike(
        @LoginUser loginUser: User,
        @PathVariable @Positive storeId: Long,
    ): ApiResponse<Long> {
        val data = storeService.storeLike(userEmail = loginUser.username, storeId = storeId)

        return ApiResponse.success(data)
    }

    @DeleteMapping("/like/{storeId}")
    fun storeLikeCancel(
        @LoginUser loginUser: User,
        @PathVariable @Positive storeId: Long,
    ): ApiResponse<Long> {
        val data = storeService.storeLikeCancel(userEmail = loginUser.username, storeId = storeId)

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
