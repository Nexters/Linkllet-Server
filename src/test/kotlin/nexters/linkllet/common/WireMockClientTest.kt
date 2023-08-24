package nexters.linkllet.common

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.TestPropertySource

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@AutoConfigureWireMock(port = 0)
@TestPropertySource(
    properties = [
        "oauth.kakao.token-host=http://localhost:\${wiremock.server.port}",
        "oauth.kakao.email-host=http://localhost:\${wiremock.server.port}"
    ]
)
@ExtendWith(ClientMocksExtension::class)
annotation class WireMockClientTest()
