package nexters.linkllet.folder.service

import nexters.linkllet.article.domain.Article
import nexters.linkllet.folder.domain.Folder
import nexters.linkllet.folder.domain.FolderRepository
import nexters.linkllet.folder.domain.findByIdOrThrow
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
    fun lookupList(deviceId: String): FolderLookupListResponse {
        val findMember = memberRepository.findByDeviceIdOrThrow(deviceId)

        val folderDtoList = folderRepository
            .findAllByMemberId(findMember.id)
            .stream()
            .map(FolderLookupDto::of)
            .toList()

        return FolderLookupListResponse(folderDtoList)
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
}
