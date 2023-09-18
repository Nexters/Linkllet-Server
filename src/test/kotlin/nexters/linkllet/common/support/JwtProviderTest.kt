package nexters.linkllet.common.support

import nexters.linkllet.common.exception.dto.UnauthorizedException
import nexters.linkllet.config.JwtConfigProperties
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class JwtProviderTest {

    @Test
    @DisplayName("토큰을 성공적으로 발급하고 payload 추출한다")
    fun getPayloadByValidToken() {
        // given
        val jwtConfigProperties = JwtConfigProperties(VALID_SECRET_KEY, EXPIRATION_TIME)
        val tokenProvider = JwtProvider(jwtConfigProperties)
        val expected = "kth990303@naver.com"
        val token = tokenProvider.generateToken(expected)

        // when
        val actual = tokenProvider.getPayload(token)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    @DisplayName("잘못된 키로 토큰을 발급할 경우 payload 추출 시 예외를 반환한다")
    fun getPayloadByInvalidToken() {
        // given
        val jwtConfigProperties = JwtConfigProperties(INVALID_SECRET_KEY, EXPIRATION_TIME)
        val invalidProvider = JwtProvider(jwtConfigProperties)

        // when
        // then
        assertThrows<UnauthorizedException> { invalidProvider.getPayload("kth990303@naver.com") }
    }

    @Test
    @DisplayName("만료된 토큰에서 payload 추출 시 예외를 반환한다")
    fun getPayloadByExpiredToken() {
        // given
        val jwtConfigProperties = JwtConfigProperties(VALID_SECRET_KEY, "-1")
        val expiredTokenProvider = JwtProvider(jwtConfigProperties)

        // when
        // then
        assertThrows<UnauthorizedException> { expiredTokenProvider.getPayload("kth990303@naver.com") }
    }

    companion object {
        private const val VALID_SECRET_KEY = "testtesttesttesttesttesttesttest"
        private const val INVALID_SECRET_KEY = "wrongtesttesttesttesttesttesttest"
        private const val EXPIRATION_TIME: String = "3600000"
    }
}
