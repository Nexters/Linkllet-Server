package nexters.linkllet.common.support.kakao.client

import nexters.linkllet.common.support.kakao.KakaoAccessToken
import nexters.linkllet.common.support.kakao.KakaoAccessTokenRequest
import nexters.linkllet.config.KakaoFeignConfiguration
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.PostMapping


@FeignClient(
    name = "kakao-access-token-client",
    url = "\${oauth.kakao.token-host}",
    configuration = [KakaoFeignConfiguration::class]
)
interface KakaoAccessTokenClient {

    @PostMapping("/oauth/token")
    fun getToken(@SpringQueryMap request: KakaoAccessTokenRequest): KakaoAccessToken
}
