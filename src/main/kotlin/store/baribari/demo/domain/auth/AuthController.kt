package store.baribari.demo.domain.auth

import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import store.baribari.demo.common.config.properties.AppProperties
import store.baribari.demo.common.dto.ApiResponse
import store.baribari.demo.common.repository.REFRESH_TOKEN
import store.baribari.demo.common.util.addCookie
import store.baribari.demo.common.util.deleteCookie
import store.baribari.demo.domain.auth.dto.TokenResponseDto
import store.baribari.demo.domain.auth.dto.UserInfoResponseDto
import store.baribari.demo.domain.auth.dto.UserLoginRequestDto
import store.baribari.demo.domain.auth.dto.UserSignUpDto
import store.baribari.demo.domain.auth.service.AuthService
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val appProperties: AppProperties,
) {
    @PostMapping("/signup")
    fun signUp(
        @RequestBody @Validated userSignUpDto: UserSignUpDto,
    ): UUID = authService.saveUser(userSignUpDto)

    @GetMapping("/info")
    fun getUserInfo(
        @LoginUser loginUser: User,
    ): ApiResponse<UserInfoResponseDto> {
        val userInfo = authService.getUserInfo(loginUser.username)
        return ApiResponse.success(UserInfoResponseDto(userInfo))
    }

    @PostMapping("/login")
    fun login(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @RequestBody userLoginRequestDto: UserLoginRequestDto,
    ): ApiResponse<UserInfoResponseDto> {
        val userInfoDto = authService.loginUser(userLoginRequestDto)

        val cookieMaxAge = appProperties.auth.refreshTokenExpiry / 1000

        deleteCookie(request, response, REFRESH_TOKEN)
        addCookie(response, REFRESH_TOKEN, userInfoDto.refreshToken!!, cookieMaxAge)

        return ApiResponse.success(UserInfoResponseDto(userInfoDto))
    }

    @GetMapping("/refresh")
    fun refresh(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ApiResponse<TokenResponseDto> {
        val (accessToken, refreshToken) = authService.refreshUserToken(request)

        if (refreshToken != null) {
            val cookieMaxAge = appProperties.auth.refreshTokenExpiry / 1000

            deleteCookie(request, response, REFRESH_TOKEN)
            addCookie(response, REFRESH_TOKEN, refreshToken, cookieMaxAge)
        }

        return ApiResponse.success(TokenResponseDto(accessToken))
    }
}
