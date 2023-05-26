package store.baribari.demo.common.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val auth: Auth,
    val oAuth2: OAuth2,
) {
    data class Auth(
        val tokenSecret: String,
        val tokenExpiry: Long,
        val refreshTokenExpiry: Long,
    )

    data class OAuth2(
        var authorizedRedirectUris: List<String>,
    )
}
