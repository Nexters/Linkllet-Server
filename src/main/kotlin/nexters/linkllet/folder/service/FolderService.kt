package nexters.linkllet.folder.service

import nexters.linkllet.article.domain.Article
import nexters.linkllet.folder.domain.Folder
import nexters.linkllet.folder.domain.FolderRepository
import nexters.linkllet.folder.domain.findByIdOrThrow
import nexters.linkllet.folder.dto.ArticleLookupDto
import nexters.linkllet.folder.dto.ArticleLookupListResponse
import nexters.linkllet.folder.dto.FolderLookupDto
import nexters.linkllet.folder.dto.FolderLookupListResponse
import nexters.linkllet.member.domain.MemberRepository
import nexters.linkllet.member.domain.findByDeviceIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.streams.toList

@Service
@Transactional
class FolderService(
    private val folderRepository: FolderRepository,
    private val memberRepository: MemberRepository,
) {

    fun createFolder(folderName: String, deviceId: String) {
        val findMember = memberRepository.findByDeviceIdOrThrow(deviceId)

        if (folderRepository.existsByMemberIdAndName(findMember.id, folderName)) {
            throw IllegalStateException("이미 사용된 폴더 이름 입니다")
        }

        folderRepository.save(Folder(folderName, findMember.id))
    }

    @Transactional(readOnly = true)
    fun lookupFolderList(deviceId: String): FolderLookupListResponse {
        val findMember = memberRepository.findByDeviceIdOrThrow(deviceId)

        return folderRepository.findAllByMemberId(findMember.id)
                .map { FolderLookupDto.of(it) }
                .let(::FolderLookupListResponse)
    }

    fun deleteFolder(folderId: Long, deviceId: String) {
        val findMember = memberRepository.findByDeviceIdOrThrow(deviceId)

        folderRepository.deleteByMemberIdAndId(findMember.id, folderId)
    }

    fun addArticleAtFolder(folderId: Long, name: String, url: String, deviceId: String) {
        val findMember = memberRepository.findByDeviceIdOrThrow(deviceId)
        val findFolder = folderRepository.findByIdOrThrow(folderId)

        findFolder.addArticle(Article(url, name, findMember.id, findFolder))
    }

    @Transactional(readOnly = true)
    fun lookupArticleList(folderId: Long, deviceId: String): ArticleLookupListResponse {
        return folderRepository.findByIdOrThrow(folderId)
                .getArticles()
                .map { ArticleLookupDto.of(it) }
                .let(::ArticleLookupListResponse)
    }

    fun deleteArticleAtFolder(folderId: Long, articleId: Long, deviceId: String) {
        val findMember = memberRepository.findByDeviceIdOrThrow(deviceId)
        val findFolder = folderRepository.findByIdOrThrow(folderId)

        findFolder.deleteArticleById(articleId, findMember.id)
    }
}
