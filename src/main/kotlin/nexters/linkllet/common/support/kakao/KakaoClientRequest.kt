package nexters.linkllet.common.support.kakao

import com.fasterxml.jackson.annotation.JsonProperty

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
