package nexters.linkllet.folder.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import nexters.linkllet.folder.dto.ArticleCreateRequest
import nexters.linkllet.folder.dto.FolderCreateRequest
import nexters.linkllet.folder.service.FolderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Folders-Command", description = "폴더 작업")
@RestController
@RequestMapping("/api/v1/folders")
class FolderCommandApi(
        private val folderService: FolderService,
) {

    companion object {
        private const val DEVICE_ID_HEADER_KEY: String = "Device-Id"
    }

    @Operation(summary = "폴더 생성")
    @SecurityRequirement(name = "DeviceId")
    @PostMapping
    fun createFolder(
            @RequestBody request: FolderCreateRequest,
            @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.createFolder(request.name, deviceId)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "폴더 삭제")
    @SecurityRequirement(name = "DeviceId")
    @DeleteMapping("/{id}")
    fun deleteFolder(
            @PathVariable id: Long,
            @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.deleteFolder(id, deviceId)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "링크 생성")
    @SecurityRequirement(name = "DeviceId")
    @PostMapping("/{id}/articles")
    fun createArticle(
            @PathVariable id: Long,
            @RequestBody request: ArticleCreateRequest,
            @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.addArticleAtFolder(id, request.name, request.url, deviceId)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "링크 삭제")
    @SecurityRequirement(name = "DeviceId")
    @DeleteMapping("/{id}/articles/{articleId}")
    fun deleteArticle(
            @PathVariable id: Long,
            @PathVariable articleId: Long,
            @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.deleteArticleAtFolder(id, articleId, deviceId)
        return ResponseEntity.noContent().build()
    }
}
