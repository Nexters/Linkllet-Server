package nexters.linkllet.common.support.kakao.mock

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.util.StreamUtils
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

// 참고: https://www.baeldung.com/spring-cloud-feign-integration-tests
class KakaoAuthMocks {

    companion object {
        private const val USER_EMAIL_REQUEST_URL = "/v2/user/me"
        private const val ACCESS_TOKEN_REQUEST_URL = "/oauth/token"
        private var addressSearchStub: StubMapping? = null
        private var addressConvertStub: StubMapping? = null

        fun startAllMocks() {
            setupUserEmailStub()
            setupAccessTokenStub()
        }

        fun removeAllMocks() {
            WireMock.removeStub(addressSearchStub)
            WireMock.removeStub(addressConvertStub)
        }

        private fun setupUserEmailStub() {
            addressSearchStub = WireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo(USER_EMAIL_REQUEST_URL))
                    .willReturn(
                        WireMock.aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                            .withBody(getMockResponseBodyByPath("payload/get-kakao-user-email-response.json"))
                    )
            )
        }

        private fun setupAccessTokenStub() {
            addressConvertStub = WireMock.stubFor(
                WireMock.post(WireMock.urlPathEqualTo(ACCESS_TOKEN_REQUEST_URL))
                    .willReturn(
                        WireMock.aResponse()
                            .withStatus(HttpStatus.OK.value())
                            .withHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                            .withBody(getMockResponseBodyByPath("payload/get-kakao-acess-token-response.json"))
                    )
            )
        }

        private fun getMockResponseBodyByPath(path: String): String? {
            try {
                return StreamUtils.copyToString(getMockResourceStream(path), Charset.defaultCharset())
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        private fun getMockResourceStream(path: String): InputStream {
            return KakaoAuthMocks::class.java.classLoader.getResourceAsStream(path)
        }
    }
}
