package nexters.linkllet.common.support.kakao

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "oauth.kakao")
data class KakaoOAuthConfigProperties (
    val clientId: String = "",
    val redirectUri: String = "",
)
