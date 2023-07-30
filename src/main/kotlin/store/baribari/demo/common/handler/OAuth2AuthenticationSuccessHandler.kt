package store.baribari.demo.common.handler

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.web.util.WebUtils.getCookie
import store.baribari.demo.auth.AuthToken
import store.baribari.demo.auth.AuthTokenProvider
import store.baribari.demo.auth.OAuth2UserInfoFactory
import store.baribari.demo.auth.ProviderType
import store.baribari.demo.common.config.properties.AppProperties
import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.UnAuthorizedException
import store.baribari.demo.common.util.addCookie
import store.baribari.demo.common.util.deleteCookie
import store.baribari.demo.common.util.log
import store.baribari.demo.model.User
import store.baribari.demo.repository.UserRepository
import store.baribari.demo.repository.common.OAuth2AuthorizationRequestBasedOnCookieRepository
import store.baribari.demo.repository.common.REDIRECT_URI_PARAM_COOKIE_NAME
import store.baribari.demo.repository.common.REFRESH_TOKEN
import store.baribari.demo.repository.common.RedisRepository
import java.net.URI
import java.util.*
import javax.persistence.EntityNotFoundException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationSuccessHandler(
    private val appProperties: AppProperties,
    private val authorizationRequestRepository: OAuth2AuthorizationRequestBasedOnCookieRepository,
    private val tokenProvider: AuthTokenProvider,
    private val redisRepository: RedisRepository,
    private val userRepository: UserRepository,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val targetUrl = determineTargetUrl(request, response, authentication)

        if (response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }
        log.info("OAuth2AuthenticationSuccessHandler.onAuthenticationSuccess: $targetUrl")
        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    override fun determineTargetUrl(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ): String {
        val targetUrl = getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)?.value ?: defaultTargetUrl
        validateRedirectTargetUrl(targetUrl)

        val findUser = findUserByAuthToken(authentication)
        val (accessToken, refreshToken) = createTokens(findUser)
        setRefreshTokenCookie(request, response, refreshToken)
        log.info(refreshToken.toString())
        return UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("token", accessToken.token)
            .build().toUriString()
    }

    private fun validateRedirectTargetUrl(targetUrl: String) {
        targetUrl.let {
            if (!isAuthorizedRedirectUri(it)) {
                throw UnAuthorizedException(
                    ErrorCode.UNAUTHORIZED,
                    "올바르지 않은 redirect uri ( $it ) 입니다.",
                )
            }
        }
    }

    private fun findUserByAuthToken(authentication: Authentication): User {
        val authToken = authentication as OAuth2AuthenticationToken
        val providerType = ProviderType.valueOf(authToken.authorizedClientRegistrationId.uppercase(Locale.getDefault()))

        val user = authentication.principal as OidcUser
        val userInfo = OAuth2UserInfoFactory.getOauth2UserInfo(providerType, user.attributes)

        return userRepository.findByEmailOrProviderId(userInfo.email, userInfo.id)
            ?: throw EntityNotFoundException(
                "유저를 찾을 수 없습니다. email = [${userInfo.email}], providerId = [${userInfo.id}] )",
            )
    }

    private fun setRefreshTokenCookie(
        request: HttpServletRequest,
        response: HttpServletResponse,
        refreshToken: AuthToken,
    ) {
        val cookieMaxAge = appProperties.auth.refreshTokenExpiry / 1000
        val LongCookieMaxAge = cookieMaxAge.toLong() // type변환...되나?
        deleteCookie(request, response, REFRESH_TOKEN)
        addCookie(response, REFRESH_TOKEN, refreshToken.token, LongCookieMaxAge)
    }

    private fun createTokens(findUser: User): Pair<AuthToken, AuthToken> {
        val now = Date()
        val tokenExpiry = appProperties.auth.tokenExpiry
        val refreshTokenExpiry = appProperties.auth.refreshTokenExpiry

        val accessToken = tokenProvider.createAuthToken(
            findUser.email,
            Date(now.time + tokenExpiry),
            findUser.role.code,
        )

        val refreshToken = tokenProvider.createAuthToken(
            findUser.email,
            Date(now.time + refreshTokenExpiry),
        )
        redisRepository.setRefreshTokenByEmail(findUser.email, refreshToken.token)

        return accessToken to refreshToken
    }

    fun clearAuthenticationAttributes(request: HttpServletRequest, response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }

    private fun isAuthorizedRedirectUri(uri: String): Boolean {
        val clientRedirectUri = URI.create(uri)
        return appProperties.oAuth2.authorizedRedirectUris
            .any {
                val authorizedURI = URI.create(it)
                authorizedURI.host.equals(clientRedirectUri.host, ignoreCase = true) &&
                        authorizedURI.port == clientRedirectUri.port
            }
    }
}
