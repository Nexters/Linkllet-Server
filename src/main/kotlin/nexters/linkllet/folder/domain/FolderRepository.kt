package nexters.linkllet.folder.domain

import nexters.linkllet.common.exception.dto.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

interface FolderRepository : JpaRepository<Folder, Long>, FolderQueryRepository {

    fun existsByMemberIdAndName(memberId: Long, name: String): Boolean

    fun deleteByMemberIdAndId(memberId: Long, id: Long)
}

fun FolderRepository.findByIdOrThrow(folderId: Long): Folder {
    return findByIdOrNull(folderId)
        ?: throw NotFoundException("해당 폴더가 없어요")
}
