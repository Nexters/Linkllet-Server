package nexters.linkllet.domain.article

import nexters.linkllet.article.domain.Article
import nexters.linkllet.common.exception.dto.BadRequestException
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("도메인 : Article")
class ArticleTest {

    @DisplayName("아티클 생성 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["http://www.gurubee.net/postgresql/basic", "https://blogshine.tistory.com/", "https://kth990303.tistory.com/", "www.naver.com"])
    fun create_article(uri: String) {
        assertThatCode { Article(uri, "test") }.doesNotThrowAnyException()
    }

    @DisplayName("아티클 uri 형식 검증 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["http//www.gurubee.net/postgresql/basic", "https:/blogshine.tistory.com/", "https://kth990303."])
    fun article_invalid_uri(uri: String) {
        assertThrows<BadRequestException> { Article(uri, "test") }
    }

    @Test
    @DisplayName("링크 제목을 10자를 초과하면 예외를 반환")
    fun change_long_folder_name() {
        assertThrows<BadRequestException> { Article("https://kth990303.tistory.com/", "test_test_t") }
    }

    @Test
    @DisplayName("링크 url 공백일 경우 예외를 반환")
    fun link_blank_validate() {
        assertThrows<BadRequestException> { Article("", "test_test_t") }
    }

    @Test
    @DisplayName("링크 url 길이 제한 초과하면 예외를 반환")
    fun link_length_validate() {
        // length: 1001
        val longLink = "https://www.google.com/search?q=%EC%95%88%EB%8dsfajaslkfjasdkfjkldsafjlsadkjf" +
                "lksajfldkadsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfalksdfjlasjf;" +
                "ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfalksdfjlasjf;ljlf" +
                "sadjlafkj;lkdjalkfjads;jfalksdfjlasjf;ljdsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafk" +
                "j;lkdjalkfjads;jfalksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;" +
                "lkdjalkfjads;jfalksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkd" +
                "jalkfjads;jfalksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkf" +
                "jads;jfalksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads" +
                ";jfalksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfal" +
                "ksdfjlasjf;ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfalksdfjl" +
                "asjf;ljlfsadlfsadkj;lasfkj5%95%ED%95%98%EC%84%B8%EC%969i57j46i433i512l2j0i131i433i512l3j4" +
                "6i131i433i512j69i60.3640j0j7&sourceid=chrome&ie=UTF-8"

        assertThrows<BadRequestException> { Article(longLink, "test") }
    }
}
