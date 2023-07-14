package nexters.linkllet.folder.presentation

import nexters.linkllet.common.support.AccessDeviceId
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

    @GetMapping
    fun lookUpFolderList(
            @AccessDeviceId deviceId: String,
    ): ResponseEntity<FolderLookupListResponse> {
        val lookupDtoList = folderService.lookupFolderList(deviceId)
        return ResponseEntity.ok().body(lookupDtoList)
    }

    @GetMapping("/{id}/articles")
    fun lookUpArticleList(
            @PathVariable id: Long,
            @AccessDeviceId deviceId: String,
    ): ResponseEntity<ArticleLookupListResponse> {
        val lookupDtoList = folderService.lookupArticleList(id, deviceId)
        return ResponseEntity.ok().body(lookupDtoList)
    }
}
