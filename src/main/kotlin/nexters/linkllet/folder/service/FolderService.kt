package nexters.linkllet.folder.service

import nexters.linkllet.article.domain.Article
import nexters.linkllet.article.domain.ArticleQueryRepository
import nexters.linkllet.common.exception.dto.BadRequestException
import nexters.linkllet.folder.domain.Folder
import nexters.linkllet.folder.domain.FolderRepository
import nexters.linkllet.folder.domain.findByIdOrThrow
import nexters.linkllet.folder.dto.ArticleLookupDto
import nexters.linkllet.folder.dto.ArticleLookupListResponse
import nexters.linkllet.folder.dto.FolderLookupListResponse
import nexters.linkllet.member.domain.Member
import nexters.linkllet.member.domain.MemberRepository
import nexters.linkllet.member.domain.findByEmailOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FolderService(
        private val folderRepository: FolderRepository,
        private val memberRepository: MemberRepository,
        private val articleQueryRepository: ArticleQueryRepository,
) {
    companion object {
        private val SPACE_REGEX = "\\s+".toRegex()
    }

    fun createFolder(folderName: String, email: String) {
        val findMember = memberRepository.findByEmailOrThrow(email)

        validateDuplicateFolderName(findMember, folderName)

        folderRepository.save(Folder(folderName, findMember.getId))
    }

    @Transactional(readOnly = true)
    fun lookupFolderList(email: String): FolderLookupListResponse {
        val findMember = memberRepository.findByEmailOrThrow(email)

        return folderRepository.lookupFolderDtosByMemberId(findMember.getId)
                .let(::FolderLookupListResponse)
    }

    fun updateFolderName(folderId: Long, updateName: String, email: String) {
        val findMember = memberRepository.findByEmailOrThrow(email)
        validateDuplicateFolderName(findMember, updateName)

        val findFolder = folderRepository.findByIdOrThrow(folderId)
        findFolder.changeFolderName(updateName)
    }

    private fun validateDuplicateFolderName(findMember: Member, folderName: String) {
        if (folderRepository.existsByMemberIdAndName(findMember.getId, folderName)) {
            throw BadRequestException("이미 사용된 폴더 이름 입니다")
        }
    }


    fun deleteFolder(folderId: Long, email: String) {
        val findMember = memberRepository.findByEmailOrThrow(email)

        folderRepository.deleteByMemberIdAndId(findMember.getId, folderId)
    }

    fun addArticleAtFolder(folderId: Long, name: String, url: String, email: String) {
        val findMember = memberRepository.findByEmailOrThrow(email)
        val findFolder = folderRepository.findByIdOrThrow(folderId)

        findFolder.addArticle(Article(url, name, findMember.getId, findFolder))
    }

    @Transactional(readOnly = true)
    fun lookupArticleList(folderId: Long, email: String): ArticleLookupListResponse {
        return folderRepository.findByIdOrThrow(folderId)
                .getArticles()
                .map { ArticleLookupDto.of(it) }
                .let(::ArticleLookupListResponse)
    }

    fun deleteArticleAtFolder(folderId: Long, articleId: Long, email: String) {
        val findMember = memberRepository.findByEmailOrThrow(email)
        val findFolder = folderRepository.findByIdOrThrow(folderId)

        findFolder.deleteArticleById(articleId, findMember.getId)
    }

    @Transactional(readOnly = true)
    fun searchArticlesByContent(content: String, email: String): ArticleLookupListResponse {
        val findMember = memberRepository.findByEmailOrThrow(email)

        val searchResults = articleQueryRepository
                .searchAllArticleByKeywords(splitBySpace(content), findMember.getId)

        return ArticleLookupListResponse(searchResults)
    }

    private fun splitBySpace(content: String): List<String> {
        return content.trim().split(SPACE_REGEX)
    }
}
