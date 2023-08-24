package nexters.linkllet.common.support.kakao

import feign.FeignException
import nexters.linkllet.common.exception.dto.UnauthorizedException
import nexters.linkllet.common.support.kakao.client.KakaoAccessTokenClient
import nexters.linkllet.common.support.kakao.client.KakaoUserInfoClient
import org.springframework.stereotype.Component


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
}
