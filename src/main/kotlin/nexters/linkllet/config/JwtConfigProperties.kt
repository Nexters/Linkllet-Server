package nexters.linkllet.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "security.jwt.token")
data class JwtConfigProperties(
        val secretKey: String,
        val expireLength: String,
)
