package nexters.linkllet.folder.dto

import nexters.linkllet.article.domain.Article

data class ArticleCreateRequest(
    val name: String,
    val url: String,
)

data class ArticleLookupDto(
    val id: Long,
    val name: String,
    val url: String,
) {
    companion object {
        fun of(article: Article): ArticleLookupDto = ArticleLookupDto(article.getId, article.getTitle, article.getLink)
    }
}

data class ArticleLookupListResponse(val articleList: List<ArticleLookupDto>)
