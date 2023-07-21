package nexters.linkllet.folder.dto

import com.querydsl.core.annotations.QueryProjection
import nexters.linkllet.folder.domain.FolderType

data class FolderCreateRequest(val name: String = "untitled")

data class FolderUpdateRequest(
    val updateName: String,
)

data class FolderLookupDto @QueryProjection constructor(
    val id: Long,
    val name: String,
    val type: FolderType,
    val size: Int,
)

data class FolderLookupListResponse(val folderList: List<FolderLookupDto>)
