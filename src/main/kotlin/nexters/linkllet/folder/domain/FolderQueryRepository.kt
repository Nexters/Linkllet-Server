package nexters.linkllet.folder.domain

import nexters.linkllet.folder.dto.FolderLookupDto

interface FolderQueryRepository {

    fun lookupFolderDtosByMemberId(memberId: Long): List<FolderLookupDto>
}
