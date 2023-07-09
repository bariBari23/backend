package store.baribari.demo.controller


import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import store.baribari.demo.auth.LoginUser
import store.baribari.demo.common.config.properties.AppProperties
import store.baribari.demo.common.util.addCookie
import store.baribari.demo.common.util.deleteCookie
import store.baribari.demo.dto.TokenResponseDto
import store.baribari.demo.dto.UserInfoResponseDto
import store.baribari.demo.dto.UserLoginRequestDto
import store.baribari.demo.dto.UserSignUpDto
import store.baribari.demo.dto.common.ApiResponse
import store.baribari.demo.repository.common.REFRESH_TOKEN
import store.baribari.demo.service.AuthService
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val appProperties: AppProperties,
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody @Validated userSignUpDto: UserSignUpDto): UUID {
        return authService.saveUser(userSignUpDto)
    }

    @GetMapping("/info")
    fun getUserInfo(@LoginUser loginUser: User): ApiResponse<UserInfoResponseDto> {
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
