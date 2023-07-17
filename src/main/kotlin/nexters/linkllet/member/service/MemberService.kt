package nexters.linkllet.member.service

import nexters.linkllet.common.exception.dto.ConflictException
import nexters.linkllet.folder.domain.Folder
import nexters.linkllet.folder.domain.FolderRepository
import nexters.linkllet.folder.domain.FolderType
import nexters.linkllet.member.domain.Member
import nexters.linkllet.member.domain.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private const val DEFAULT_FOLDER_NAME = "default"

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val folderRepository: FolderRepository,
) {

    fun signUp(deviceId: String) {
        memberRepository.findByDeviceId(deviceId)
            ?.let { throw ConflictException() }

        val newMember = memberRepository.save(Member(deviceId))
        folderRepository.save(Folder(DEFAULT_FOLDER_NAME, newMember.getId, FolderType.DEFAULT))
    }
}
