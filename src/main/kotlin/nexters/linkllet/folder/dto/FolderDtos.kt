package nexters.linkllet.folder.dto

import nexters.linkllet.folder.domain.Folder

data class FolderCreateRequest(val name:String = "untitled")

data class FolderLookupDto(
    val id: Long,
    val name: String,
) {
    companion object {
        fun of(folder: Folder): FolderLookupDto = FolderLookupDto(folder.getId, folder.name)
    }
}

data class FolderLookupListResponse(val folderList: List<FolderLookupDto>)
