package nexters.linkllet.domain

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("도메인 : Article")
class ArticleTest {

    @DisplayName("아티클 생성 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["http://www.gurubee.net/postgresql/basic", "https://blogshine.tistory.com/", "https://kth990303.tistory.com/"])
    fun create_article(uri: String) {
        assertThatCode { Article(uri, "test") }.doesNotThrowAnyException()
    }

    @DisplayName("아티클 uri 형식 검증 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["http//www.gurubee.net/postgresql/basic", "https:/blogshine.tistory.com/", "https://kth990303."])
    fun article_invalid_uri(uri: String) {
        assertThrows<IllegalArgumentException> { Article(uri, "test") }
    }
}
