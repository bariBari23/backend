package store.baribari.demo.domain.auth

import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import store.baribari.demo.common.enums.ErrorCode
import store.baribari.demo.common.exception.UnAuthorizedException
import java.security.Key
import java.util.Date

class AuthTokenProvider(
    secret: String,
) {
    private val key: Key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun createAuthToken(
        email: String,
        expiry: Date,
        role: String? = null,
    ): AuthToken = AuthToken(email, expiry, key, role)

    fun convertAuthToken(token: String): AuthToken = AuthToken(token, key)

    fun getAuthentication(authToken: AuthToken): Authentication {
        if (authToken.isValid) {
            val claims = authToken.tokenClaims
            val authorities =
                arrayOf(claims!![AUTHORITIES_KEY].toString())
                    .map(::SimpleGrantedAuthority)
                    .toList()

            val principal = User(claims.subject, "", authorities)

            return UsernamePasswordAuthenticationToken(principal, authToken, authorities)
        } else {
            throw UnAuthorizedException(ErrorCode.TOKEN_INVALID, "올바르지 않은 Token입니다.")
        }
    }
}
