package nexters.linkllet.common.support.kakao

import nexters.linkllet.common.WireMockClientTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@WireMockClientTest
class KakaoOAuthUserProviderTest(
    @Autowired val kakaoOAuthUserProvider: KakaoOAuthUserProvider,
) {

    @Test
    fun `카카오 로그인으로 사용자 email 정보를 응답 받는다`() {
        // given
        val authorizationCode = "test_authorization_code"

        // when
        val kakaoPlatformUser = kakaoOAuthUserProvider.getKakaoPlatformUser(authorizationCode)

        // then
        assertThat(kakaoPlatformUser.getEmail()).isEqualTo("test@sample.com")
    }
}
