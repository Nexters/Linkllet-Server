package nexters.linkllet.common.support.apple

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "oauth.apple")
data class AppleOAuthConfigProperties(
    val iss: String = "iss",
    val clientId: String = "aud",
    val nonce: String = "nonce",
)
