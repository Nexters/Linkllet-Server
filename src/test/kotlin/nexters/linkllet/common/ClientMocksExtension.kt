package nexters.linkllet.common

import nexters.linkllet.common.support.kakao.mock.KakaoAuthMocks
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class ClientMocksExtension : BeforeAllCallback, AfterAllCallback {

    override fun beforeAll(context: ExtensionContext?) {
        KakaoAuthMocks.startAllMocks()
    }

    override fun afterAll(context: ExtensionContext?) {
        KakaoAuthMocks.removeAllMocks()
    }
}
