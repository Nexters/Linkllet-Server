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

        if(folderRepository.existsByMemberIdAndName(findMember.id, folderName)) {
            throw IllegalStateException("이미 사용된 폴더 이름 입니다")
        }

        folderRepository.save(Folder(folderName, findMember.id))
    }
}
