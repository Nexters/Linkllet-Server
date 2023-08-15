package nexters.linkllet.common.support.apple

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class AppleClientTest(
        @Autowired val appleClient: AppleClient,
) {
    @Test
    fun `apple 서버와 통신하여 Apple public keys 응답을 받는다`() {
        assertDoesNotThrow { appleClient.getApplePublicKeys().keys }
    }
}
