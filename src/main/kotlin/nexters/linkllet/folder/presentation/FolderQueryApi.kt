package nexters.linkllet.folder.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import nexters.linkllet.common.support.AccessDeviceId
import nexters.linkllet.folder.dto.ArticleLookupListResponse
import nexters.linkllet.folder.dto.FolderLookupListResponse
import nexters.linkllet.folder.service.FolderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Folders-Query", description = "폴더 조회")
@RestController
@RequestMapping("/api/v1/folders")
class FolderQueryApi(
        private val folderService: FolderService,
) {

    @Operation(summary = "폴더 목록 조회")
    @SecurityRequirement(name = "Device-Id")
    @GetMapping
    fun lookUpFolderList(
            @AccessDeviceId deviceId: String,
    ): ResponseEntity<FolderLookupListResponse> {
        val lookupDtoList = folderService.lookupFolderList(deviceId)
        return ResponseEntity.ok().body(lookupDtoList)
    }

    @Operation(summary = "기사 목록 조회")
    @SecurityRequirement(name = "Device-Id")
    @GetMapping("/{id}/articles")
    fun lookUpArticleList(
            @PathVariable id: Long,
            @AccessDeviceId deviceId: String,
    ): ResponseEntity<ArticleLookupListResponse> {
        val lookupDtoList = folderService.lookupArticleList(id, deviceId)
        return ResponseEntity.ok().body(lookupDtoList)
    }
}
