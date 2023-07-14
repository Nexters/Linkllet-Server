package nexters.linkllet.folder.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

interface FolderRepository: JpaRepository<Folder, Long> {

    fun existsByMemberIdAndName(memberId: Long, name: String): Boolean

    fun findAllByMemberId(memberId: Long): List<Folder>

    fun deleteByMemberIdAndId(memberId: Long, id: Long): Unit
}

fun FolderRepository.findByIdOrThrow(folderId: Long): Folder {
    return findByIdOrNull(folderId)
        ?: throw IllegalArgumentException("해당 폴더가 없어요")
}
