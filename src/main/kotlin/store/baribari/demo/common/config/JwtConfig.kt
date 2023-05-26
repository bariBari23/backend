package store.baribari.demo.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import store.baribari.demo.auth.AuthTokenProvider

@Configuration
class JwtConfig(
    // TODO: 값 변경하기
    @Value("\${jwt.secret}")
    private val secret: String,
) {
    @Bean
    fun jwtTokenProvider(): AuthTokenProvider {
        return AuthTokenProvider(secret)
    }
}
