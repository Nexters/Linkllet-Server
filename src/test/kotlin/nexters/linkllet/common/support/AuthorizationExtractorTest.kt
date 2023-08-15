package nexters.linkllet.common.support

import nexters.linkllet.common.exception.dto.UnauthorizedException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.mock.web.MockHttpServletRequest

class AuthorizationExtractorTest {

    @Test
    @DisplayName("이메일을 추출한다")
    fun extractValidEmail() {
        // given
        val request = MockHttpServletRequest()
        val expected = "Bearer kth990303@naver.com"
        request.addHeader("Authorization", expected)

        // when
        val actual = AuthorizationExtractor.extractToken(request)

        // then
        assertThat(actual).isEqualTo("kth990303@naver.com")
    }

    @Test
    @DisplayName("토큰 형식이 Bearer로 시작하지 않으면 예외를 반환한다")
    fun extractValidBearerToken() {
        // given
        val request = MockHttpServletRequest()
        val expected = "kth990303@naver.com"
        request.addHeader("Authorization", expected)

        // when
        // then
        assertThrows<UnauthorizedException> { AuthorizationExtractor.extractToken(request) }
    }

    @Test
    @DisplayName("토큰에 이메일이 존재하지 않으면 예외를 반환한다")
    fun extractNotExistEmail() {
        // given
        val request = MockHttpServletRequest()
        val expected = "Bearer  "
        request.addHeader("Authorization", expected)

        // when
        // then
        assertThrows<UnauthorizedException> { AuthorizationExtractor.extractToken(request) }
    }

    @Test
    @DisplayName("토큰이 존재하지 않으면 예외를 반환한다")
    fun extractNotExistToken() {
        // given
        val request = MockHttpServletRequest()

        // when
        // then
        assertThrows<UnauthorizedException> { AuthorizationExtractor.extractToken(request) }
    }
}
