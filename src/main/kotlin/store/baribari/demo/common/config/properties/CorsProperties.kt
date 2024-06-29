package store.baribari.demo.common.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "cors")
data class CorsProperties(
    val allowedOrigins: String,
    val allowedMethods: String,
    val allowedHeaders: String,
    val maxAge: Long,
)
