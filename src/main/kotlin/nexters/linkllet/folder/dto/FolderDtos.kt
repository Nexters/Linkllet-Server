package nexters.linkllet.folder.dto

import nexters.linkllet.folder.domain.Folder
import nexters.linkllet.folder.domain.FolderType

data class FolderCreateRequest(val name:String = "untitled")

data class FolderLookupDto(
    val id: Long,
    val name: String,
    val type: FolderType,
) {
    companion object {
        fun of(folder: Folder): FolderLookupDto = FolderLookupDto(folder.getId, folder.name, folder.getType)
    }
}

data class FolderLookupListResponse(val folderList: List<FolderLookupDto>)
