package nexters.linkllet.common.support.kakao

import com.fasterxml.jackson.databind.ObjectMapper
import feign.FeignException
import nexters.linkllet.common.exception.dto.UnauthorizedException
import nexters.linkllet.common.support.kakao.client.KakaoAccessTokenClient
import nexters.linkllet.common.support.kakao.client.KakaoUserInfoClient
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange


@Component
class KakaoOAuthUserProvider(
    private val kakaoAccessTokenClient: KakaoAccessTokenClient,
    private val kakaoUserInfoClient: KakaoUserInfoClient,
    private val kakaoOAuthConfigProperties: KakaoOAuthConfigProperties,
) {

    fun getKakaoPlatformUser(authorizationCode: String): KakaoPlatformUser {
        val kakaoAccessTokenRequest = KakaoAccessTokenRequest(
            authorizationCode,
            kakaoOAuthConfigProperties.clientId,
            kakaoOAuthConfigProperties.redirectUri
        )

        val token: KakaoAccessToken = requestKakaoAccessToken(kakaoAccessTokenRequest)
        val kakaoUserRequest = KakaoUserRequest("[\"kakao_account.email\"]")
        return kakaoUserInfoClient.getUser(kakaoUserRequest, token.getAuthorization())
    }

    private fun requestKakaoAccessToken(kakaoAccessTokenRequest: KakaoAccessTokenRequest): KakaoAccessToken {
        try {
            return kakaoAccessTokenClient.getToken(kakaoAccessTokenRequest)
        } catch (e: FeignException) {
            throw UnauthorizedException("Kakao OAuth 인가코드 값이 올바르지 않습니다.")
        }
    }

    private fun createHeaders(): HttpHeaders? {
        val headers = HttpHeaders()
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
        return headers
    }

    private fun createParams(code: String): MultiValueMap<String, String>? {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", kakaoOAuthConfigProperties.clientId)
        params.add("redirect_uri", kakaoOAuthConfigProperties.redirectUri)
        params.add("code", code)
        return params
    }
}
