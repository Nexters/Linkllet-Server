package nexters.linkllet.folder.presentation

import nexters.linkllet.folder.dto.ArticleLookupListResponse
import nexters.linkllet.folder.dto.FolderLookupListResponse
import nexters.linkllet.folder.service.FolderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/folders")
class FolderQueryApi(
    private val folderService: FolderService,
) {

    companion object {
        private const val DEVICE_ID_HEADER_KEY: String = "Device-Id"
    }

    @GetMapping
    fun lookUpFolderList(
        @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<FolderLookupListResponse> {
        val lookupDtoList = folderService.lookupFolderList(deviceId)
        return ResponseEntity.ok().body(lookupDtoList)
    }

    @GetMapping("/{id}/articles")
    fun lookUpArticleList(
        @PathVariable id: Long,
        @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<ArticleLookupListResponse> {
        val lookupDtoList = folderService.lookupArticleList(id, deviceId)
        return ResponseEntity.ok().body(lookupDtoList)
    }
}
