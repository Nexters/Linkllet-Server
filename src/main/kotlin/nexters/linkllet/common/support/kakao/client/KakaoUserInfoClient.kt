package nexters.linkllet.common.support.kakao.client

import nexters.linkllet.common.support.kakao.KakaoPlatformUser
import nexters.linkllet.common.support.kakao.KakaoUserRequest
import nexters.linkllet.config.KakaoFeignConfiguration
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.cloud.openfeign.SpringQueryMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader


@FeignClient(
    name = "kakao-user-client",
    url = "\${oauth.kakao.email-host}",
    configuration = [KakaoFeignConfiguration::class]
)
interface KakaoUserInfoClient {

    @GetMapping("/v2/user/me")
    fun getUser(
        @SpringQueryMap request: KakaoUserRequest,
        @RequestHeader(name = "Authorization") authorization: String
    ): KakaoPlatformUser
}
