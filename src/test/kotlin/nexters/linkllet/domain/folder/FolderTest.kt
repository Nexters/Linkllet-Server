package nexters.linkllet.domain.folder

import nexters.linkllet.article.domain.Article
import nexters.linkllet.common.exception.dto.BadRequestException
import nexters.linkllet.folder.domain.Folder
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("도메인 : Folder")
class FolderTest {

    @Test
    @DisplayName("폴더명이 10자를 초과하면 예외를 반환한다.")
    fun invalid_folder_name() {
        // given
        // when
        // then
        assertThrows<BadRequestException> {
            Folder("article_folder")
        }
    }

    @Test
    @DisplayName("article을 단건으로 추가한다")
    fun add_article() {
        // given
        val articleOne = Article("https://blogshine.tistory.com/1", "article_1")
        val articleTwo = Article("https://blogshine.tistory.com/2", "article_2")
        val folder = Folder("folder")

        // when
        folder.addArticle(articleOne)
        folder.addArticle(articleTwo)

        // then
        assertThat(folder.getArticles()).extracting("title").containsExactly("article_1", "article_2")
    }

    @Test
    @DisplayName("article을 한번에 추가한다")
    fun add_all_article() {
        // given
        val articleOne = Article("https://blogshine.tistory.com/1", "article_1")
        val articleTwo = Article("https://blogshine.tistory.com/2", "article_2")
        val articleThree = Article("https://blogshine.tistory.com/3", "article_3")
        val folder = Folder("folder")

        // when
        folder.addAllArticle(articleOne, articleTwo, articleThree)

        // then
        assertThat(folder.getArticles()).extracting("title").containsExactly("article_1", "article_2", "article_3")
    }

    @Test
    @DisplayName("article을 단건으로 삭제한다")
    fun delete_article() {
        // given
        val articleOne = Article("https://blogshine.tistory.com/1", "article_1")
        val articleTwo = Article("https://blogshine.tistory.com/2", "article_2")
        val articleThree = Article("https://blogshine.tistory.com/3", "article_3")
        val folder = Folder("folder")
        folder.addAllArticle(articleOne, articleTwo, articleThree)

        // when
        folder.deleteArticle(articleTwo)

        // then
        assertThat(folder.getArticles()).extracting("title").containsExactly("article_1", "article_3")
    }

    @Test
    @DisplayName("article을 Id로 단건 삭제한다")
    fun delete_article_by_id() {
        // given
        val articleOne = Article("https://blogshine.tistory.com/1", "article_1", id = 1L)
        val articleTwo = Article("https://blogshine.tistory.com/2", "article_2", id = 2L)
        val articleThree = Article("https://blogshine.tistory.com/3", "article_3", id = 3L)
        val folder = Folder("folder")
        folder.addAllArticle(articleOne, articleTwo, articleThree)

        // when
        folder.deleteArticleById(2L, folder.getId)

        // then
        assertThat(folder.getArticles()).extracting("title").containsExactly("article_1", "article_3")
    }

    @Test
    @DisplayName("article을 한번에 삭제한다")
    fun delete_all_article() {
        // given
        val articleOne = Article("https://blogshine.tistory.com/1", "article_1")
        val articleTwo = Article("https://blogshine.tistory.com/2", "article_2")
        val articleThree = Article("https://blogshine.tistory.com/3", "article_3")
        val folder = Folder("folder")
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
        val folder = Folder("folder")
        for (i in 0..50) {
            folder.addArticle(Article("https://blogshine.tistory.com/$i", "article_$i"))
        }

        // when, then
        assertThrows<BadRequestException> {
            folder.addArticle(
                    Article(
                            "https://blogshine.tistory.com/temp",
                            "article_temp"
                    )
            )
        }
    }

    @Test
    @DisplayName("폴더명을 10자 이하로 변경한다")
    fun change_folder_name() {
        // given
        val expected = "kth990303"
        val folder = Folder("folder")

        // when
        folder.changeFolderName(expected)

        // then
        assertThat(folder.name).isEqualTo(expected)
    }

    @Test
    @DisplayName("폴더명을 10자를 초과하여 변경하면 예외를 반환한다.")
    fun change_long_folder_name() {
        // given
        val folder = Folder("folder")

        // when
        // then
        assertThrows<BadRequestException> {
            folder.changeFolderName("article_folder")
        }
    }
}
