package nexters.linkllet.article.domain

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import nexters.linkllet.article.domain.QArticle.article
import nexters.linkllet.folder.dto.ArticleLookupDto
import nexters.linkllet.folder.dto.QArticleLookupDto
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ArticleQueryRepository(
    private val queryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(Article::class.java) {

    fun searchAllArticleByKeywords(splitResult: List<String>, memberId: Long): List<ArticleLookupDto> {
        return queryFactory
            .select(
                QArticleLookupDto(
                    article.id,
                    article.title,
                    article.link._value,
                    article.createAt,
                )
            )
            .from(article)
            .where(
                article.id.`in`(JPAExpressions.select(article.id).from(article).where(article.memberId.eq(memberId)))
                    .and(isContainKeyword(splitResult))
            )
            .orderBy(article.createAt.desc())
            .fetch()
    }

    private fun isContainKeyword(keywords: List<String>): BooleanBuilder? {
        val booleanBuilder = BooleanBuilder()
        for (containedName in keywords) {
            booleanBuilder.or(article.title.contains(containedName))
        }

        return booleanBuilder
    }
}
