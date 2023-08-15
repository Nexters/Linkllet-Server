package nexters.linkllet.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "security.jwt.token")
data class JwtConfigProperties(
        var secretKey: String = "",
        var expirationTime: Long = 18000000L,
)
