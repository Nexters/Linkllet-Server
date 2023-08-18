package nexters.linkllet.common.support.kakao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoPlatformUser(
    private val id: Long,
    @JsonProperty("kakao_account") private val kakaoAccount: KakaoAccount,
) {
    fun getEmail() = this.kakaoAccount.email
}

data class KakaoAccount(
    val email: String,
)

data class KakaoAccessToken(
    @JsonProperty("access_token") private val accessToken: String,
    @JsonProperty("token_type") private val tokenType: String,
    @JsonProperty("refresh_token") private val refreshToken: String,
    @JsonProperty("expires_in") private val expiresIn: Long,
    @JsonProperty("refresh_token_expires_in") private val refreshTokenExpiresIn: Long,
    @JsonProperty("scope") private val scope: String,
) {
    fun getAuthorization(): String = AUTHORIZATION_BEARER + accessToken

    companion object {
        private const val AUTHORIZATION_BEARER = "Bearer "
    }
}
