package nexters.linkllet.folder.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import nexters.linkllet.article.domain.QArticle.article
import nexters.linkllet.folder.domain.QFolder.folder
import nexters.linkllet.folder.dto.FolderLookupDto
import nexters.linkllet.folder.dto.QFolderLookupDto

class FolderQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : FolderQueryRepository {

    override fun lookupFolderDtosByMemberId(memberId: Long): List<FolderLookupDto> {
        return queryFactory
            .select(QFolderLookupDto(folder.id, folder.name, folder.type, article.count().intValue()))
            .from(folder).leftJoin(article)
            .on(folder.id.eq(article.folder.id)).fetchJoin()
            .where(folder.memberId.eq(memberId))
            .groupBy(folder.id)
            .fetch()
    }
}
