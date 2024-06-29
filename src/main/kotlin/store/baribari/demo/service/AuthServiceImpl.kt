package store.baribari.demo.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import store.baribari.demo.auth.AUTHORITIES_KEY
import store.baribari.demo.auth.AuthTokenProvider
import store.baribari.demo.common.config.properties.AppProperties
import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.enums.Role
import store.baribari.demo.common.exception.ConditionConflictException
import store.baribari.demo.common.exception.EntityNotFoundException
import store.baribari.demo.common.exception.UnAuthorizedException
import store.baribari.demo.common.util.getAccessToken
import store.baribari.demo.common.util.getCookie
import store.baribari.demo.common.util.log
import store.baribari.demo.dto.TokenDto
import store.baribari.demo.dto.UserInfoDto
import store.baribari.demo.dto.UserLoginRequestDto
import store.baribari.demo.dto.UserSignUpDto
import store.baribari.demo.model.User
import store.baribari.demo.repository.UserRepository
import store.baribari.demo.repository.common.REFRESH_TOKEN
import store.baribari.demo.repository.common.RedisRepository
import java.util.Date
import java.util.UUID
import javax.servlet.http.HttpServletRequest

private const val THREE_DAYS_MSEC = 259200000

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val appProperties: AppProperties,
    private val authTokenProvider: AuthTokenProvider,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val redisRepository: RedisRepository,
) : AuthService {
    override fun saveUser(signUpDto: UserSignUpDto): UUID {
        checkEmailAndUserName(signUpDto.email, signUpDto.nickname)
        val user = signUpDto.toUser()
        val encodedPassword = passwordEncoder.encode(user.password)
        user.encodePassword(encodedPassword)
        return userRepository.save(user).id!!
    }

    override fun loginUser(userLoginRequestDto: UserLoginRequestDto): UserInfoDto {
        val email = userLoginRequestDto.email
        val rawPassword = userLoginRequestDto.password

        val findUser =
            userRepository.findByEmail(email)
                ?: throw EntityNotFoundException("$email 을 가진 유저는 존재하지 않습니다.")

        if (!passwordEncoder.matches(rawPassword, findUser.password)) {
            throw UnAuthorizedException(ErrorCode.PASSWORD_MISS_MATCH, "비밀번호가 일치하지 않습니다!")
        }

        val (accessToken, refreshToken) = createTokens(findUser, email)
        return UserInfoDto(findUser, accessToken, refreshToken)
    }

    override fun refreshUserToken(request: HttpServletRequest): TokenDto {
        val accessToken =
            getAccessToken(request)
                ?: throw UnAuthorizedException(ErrorCode.ACCESS_TOKEN_NOT_EXIST, "Access Token이 존재하지 않습니다.")

        val convertAccessToken = authTokenProvider.convertAuthToken(accessToken)

        val claims =
            convertAccessToken.expiredTokenClaims
                ?: throw UnAuthorizedException(ErrorCode.TOKEN_NOT_EXPIRED, "Access Token이 만료되지 않았거나 올바르지 않습니다.")

        val email = claims.subject
        val role = Role.of(claims.get(AUTHORITIES_KEY, String::class.java))

        val refreshToken =
            getCookie(request, REFRESH_TOKEN)?.value
                ?: throw UnAuthorizedException(ErrorCode.REFRESH_TOKEN_NOT_EXIST, "Refresh Token이 존재하지 않습니다.")

        val convertRefreshToken = authTokenProvider.convertAuthToken(refreshToken)

        if (!convertRefreshToken.isValid) {
            throw UnAuthorizedException(ErrorCode.TOKEN_INVALID, "올바르지 않은 토큰입니다. ( $refreshToken )")
        }

        val savedRefreshToken = redisRepository.getRefreshTokenByEmail(email)

        if (savedRefreshToken == null || savedRefreshToken != refreshToken) {
            throw UnAuthorizedException(ErrorCode.TOKEN_MISS_MATCH, "Refresh Token이 올바르지 않습니다.")
        }

        val now = Date()
        val tokenExpiry = appProperties.auth.tokenExpiry

        val newAccessToken =
            authTokenProvider.createAuthToken(
                email,
                Date(now.time + tokenExpiry),
                role.code,
            ).token

        val validTime = convertRefreshToken.tokenClaims!!.expiration.time - now.time

        if (validTime <= THREE_DAYS_MSEC) {
            val refreshTokenExpiry = appProperties.auth.refreshTokenExpiry
            val newRefreshToken =
                authTokenProvider.createAuthToken(
                    email,
                    Date(now.time + refreshTokenExpiry),
                ).token

            return TokenDto(newAccessToken, newRefreshToken)
        }

        return TokenDto(newAccessToken, null)
    }

    override fun getUserInfo(email: String): UserInfoDto {
        val findUser =
            userRepository.findByEmail(email)
                ?: throw EntityNotFoundException("$email 을 가진 유저는 존재하지 않습니다.")

        return UserInfoDto(findUser)
    }

    private fun createTokens(
        findUser: User,
        email: String,
    ): Pair<String, String> {
        val role = findUser.role

        val now = Date()
        val tokenExpiry = appProperties.auth.tokenExpiry
        val refreshTokenExpiry = appProperties.auth.refreshTokenExpiry

        val accessToken =
            authTokenProvider.createAuthToken(
                email,
                Date(now.time + tokenExpiry),
                role.code,
            ).token

        val refreshToken =
            authTokenProvider.createAuthToken(
                email,
                Date(now.time + refreshTokenExpiry),
            ).token

        redisRepository.setRefreshTokenByEmail(email, refreshToken)

        log.info("refreshToken: $refreshToken")
        log.info("accessToken: $accessToken")

        return accessToken to refreshToken
    }

    private fun checkEmailAndUserName(
        email: String,
        username: String,
    ) {
        userRepository.findByEmail(email)?.let {
            if (it.email == email) {
                throw ConditionConflictException(ErrorCode.EMAIL_DUPLICATED, "$email 은 중복 이메일입니다.")
            }
            throw ConditionConflictException(
                ErrorCode.USERNAME_DUPLICATED,
                "$username 은 중복 닉네임입니다.",
            )
        }
    }
}
