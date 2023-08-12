package nexters.linkllet.util

import nexters.linkllet.folder.domain.Folder
import nexters.linkllet.folder.domain.FolderRepository
import nexters.linkllet.folder.domain.FolderType
import nexters.linkllet.member.domain.Member
import nexters.linkllet.member.domain.MemberRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

private const val DEFAULT_FOLDER_NAME = "default"

@Component
class DatabaseLoader(
    private val memberRepository: MemberRepository,
    private val folderRepository: FolderRepository,
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!
    }

    fun loadData() {
        log.info("[call DataLoader]")

        val newMember = memberRepository.save(Member(email = "kth990303@naver.com"))
        folderRepository.save(Folder(DEFAULT_FOLDER_NAME, newMember.getId, FolderType.DEFAULT))

        log.info("[init complete DataLoader]")
    }
}
