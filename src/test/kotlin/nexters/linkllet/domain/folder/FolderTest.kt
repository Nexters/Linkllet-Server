package nexters.linkllet.domain.folder

import nexters.linkllet.article.domain.Article
import nexters.linkllet.folder.domain.Folder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("도메인 : Folder")
class FolderTest {

    @Test
    @DisplayName("article을 단건으로 추가한다")
    fun add_article() {
        // given
        val articleOne = Article("https://blogshine.tistory.com/1", "article_1")
        val articleTwo = Article("https://blogshine.tistory.com/2", "article_2")
        val folder = Folder("article_folder")

        // when
        folder.addArticle(articleOne)
        folder.addArticle(articleTwo)

        // then
        assertThat(folder.getArticles()).extracting("title").containsExactly("article_1", "article_2");
    }

    @Test
    @DisplayName("article을 한번에 추가한다")
    fun add_all_article() {
        // given
        val articleOne = Article("https://blogshine.tistory.com/1", "article_1")
        val articleTwo = Article("https://blogshine.tistory.com/2", "article_2")
        val articleThree = Article("https://blogshine.tistory.com/3", "article_3")
        val folder = Folder("article_folder")

        // when
        folder.addAllArticle(articleOne, articleTwo, articleThree)

        // then
        assertThat(folder.getArticles()).extracting("title").containsExactly("article_1", "article_2", "article_3");
    }

    @Test
    @DisplayName("article을 단건으로 삭제한다")
    fun delete_article() {
        // given
        val articleOne = Article("https://blogshine.tistory.com/1", "article_1")
        val articleTwo = Article("https://blogshine.tistory.com/2", "article_2")
        val articleThree = Article("https://blogshine.tistory.com/3", "article_3")
        val folder = Folder("article_folder")
        folder.addAllArticle(articleOne, articleTwo, articleThree)

        // when
        folder.deleteArticle(articleTwo)

        // then
        assertThat(folder.getArticles()).extracting("title").containsExactly("article_1", "article_3");
    }

    @Test
    @DisplayName("article을 한번에 삭제한다")
    fun delete_all_article() {
        // given
        val articleOne = Article("https://blogshine.tistory.com/1", "article_1")
        val articleTwo = Article("https://blogshine.tistory.com/2", "article_2")
        val articleThree = Article("https://blogshine.tistory.com/3", "article_3")
        val folder = Folder("article_folder")
        folder.addAllArticle(articleOne, articleTwo, articleThree)

        // when
        folder.deleteAll()

        // then
        assertThat(folder.getArticles()).isEmpty()
    }

    @Test
    @DisplayName("폴더의 사이즈인 50보다 많은 article을 저장할 수 없다.")
    fun invalid_folder_size() {
        // given
        val folder = Folder("article_folder")
        for (i in 0..49) {
            folder.addArticle(Article("https://blogshine.tistory.com/" + i, "article_" + i))
        }

        // when, then
        assertThrows<IllegalStateException> {
            folder.addArticle(
                Article(
                    "https://blogshine.tistory.com/temp",
                    "article_temp"
                )
            )
        }
    }
}
