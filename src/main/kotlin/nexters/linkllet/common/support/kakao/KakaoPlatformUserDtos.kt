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

data class KakaoAccessTokenRequest(
    val code: String,
    val client_id: String,
    val redirect_uri: String,
    val grant_type: String = "authorization_code",
) {
    override fun toString(): String {
        return "code=" + code + '&' +
                "client_id=" + client_id + '&' +
                "redirect_uri=" + redirect_uri + '&' +
                "grant_type=" + grant_type;
    }
}

data class KakaoUserRequest(
    @JsonProperty("property_keys") private val propertyKeys: String,
    @JsonProperty("secure_resource") private val secureResource: Boolean = true,
)
