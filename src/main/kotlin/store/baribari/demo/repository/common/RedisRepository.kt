package store.baribari.demo.repository.common

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import store.baribari.demo.common.config.properties.AppProperties
import java.util.concurrent.TimeUnit

const val PASSWORD_VERIFICATION_MINUTE = 5
const val RANKING = "ranking"

@Repository
class RedisRepository(
    private val redisTemplate: StringRedisTemplate,
    private val appProperties: AppProperties,
) {
    fun getRefreshTokenByEmail(email: String): String? {
        return redisTemplate.opsForValue().get(email)
    }

    fun setRefreshTokenByEmail(email: String, refreshToken: String) {
        redisTemplate.opsForValue()
            .set(email, refreshToken, appProperties.auth.refreshTokenExpiry, TimeUnit.MILLISECONDS)
    }

    fun setPasswordVerification(code: String, email: String) {
        redisTemplate.opsForValue().set(code, email, PASSWORD_VERIFICATION_MINUTE.toLong(), TimeUnit.MINUTES)
    }

    fun getEmailByCode(code: String): String? {
        return redisTemplate.opsForValue().get(code)
    }

    fun removePasswordVerification(code: String) {
        redisTemplate.delete(code)
    }
}
