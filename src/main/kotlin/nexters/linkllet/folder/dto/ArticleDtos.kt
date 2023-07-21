package nexters.linkllet.folder.dto

import com.fasterxml.jackson.annotation.JsonFormat
import nexters.linkllet.article.domain.Article
import java.time.LocalDateTime

data class ArticleCreateRequest(
    val name: String,
    val url: String,
)

data class ArticleLookupDto(
    val id: Long,
    val name: String,
    val url: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createAt: LocalDateTime,
) {
    companion object {
        fun of(article: Article): ArticleLookupDto = ArticleLookupDto(article.getId, article.getTitle, article.getLink, article.getCreatedDateTime!!)
    }
}

data class ArticleLookupListResponse(val articleList: List<ArticleLookupDto>)
