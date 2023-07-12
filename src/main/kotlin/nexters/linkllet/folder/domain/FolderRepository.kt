package nexters.linkllet.folder.domain

import org.springframework.data.jpa.repository.JpaRepository

interface FolderRepository: JpaRepository<Folder, Long> {

    fun existsByMemberIdAndName(memberId: Long, name: String): Boolean
}
