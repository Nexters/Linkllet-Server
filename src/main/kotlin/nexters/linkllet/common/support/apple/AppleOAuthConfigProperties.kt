package nexters.linkllet.common.support.apple

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.apple")
data class AppleOAuthConfigProperties(
        var iss: String = "iss",
        var clientId: String = "aud",
        var nonce: String = "nonce",
)
