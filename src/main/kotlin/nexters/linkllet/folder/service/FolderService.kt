package nexters.linkllet.folder.service

import nexters.linkllet.folder.domain.Folder
import nexters.linkllet.folder.domain.FolderRepository
import nexters.linkllet.member.domain.MemberRepository
import nexters.linkllet.member.domain.findByDeviceIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FolderService(
    private val folderRepository: FolderRepository,
    private val memberRepository: MemberRepository,
) {

    fun createFolder(deviceId: String, folderName: String) {
        val findMember = memberRepository.findByDeviceIdOrThrow(deviceId)
        val newFolder = folderRepository.save(Folder(folderName))
        newFolder.assignMember(findMember.id)
    }
}
