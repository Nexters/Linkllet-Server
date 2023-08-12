package nexters.linkllet.folder.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import nexters.linkllet.common.support.LoginUserEmail
import nexters.linkllet.folder.dto.ArticleCreateRequest
import nexters.linkllet.folder.dto.FolderCreateRequest
import nexters.linkllet.folder.dto.FolderUpdateRequest
import nexters.linkllet.folder.service.FolderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Folders-Command", description = "폴더 작업")
@RestController
@RequestMapping("/api/v1/folders")
class FolderCommandApi(
        private val folderService: FolderService,
) {

    @Operation(summary = "폴더 생성")
    @SecurityRequirement(name = "Device-Id")
    @PostMapping
    fun createFolder(
            @RequestBody request: FolderCreateRequest,
            @LoginUserEmail deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.createFolder(request.name, deviceId)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "폴더명 변경")
    @SecurityRequirement(name = "Device-Id")
    @PutMapping("/{id}")
    fun updateFolder(
            @PathVariable id: Long,
            @RequestBody request: FolderUpdateRequest,
            @LoginUserEmail deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.updateFolderName(id, request.updateName, deviceId)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "폴더 삭제")
    @SecurityRequirement(name = "Device-Id")
    @DeleteMapping("/{id}")
    fun deleteFolder(
            @PathVariable id: Long,
            @LoginUserEmail deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.deleteFolder(id, deviceId)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "링크 생성")
    @SecurityRequirement(name = "Device-Id")
    @PostMapping("/{id}/articles")
    fun createArticle(
            @PathVariable id: Long,
            @RequestBody request: ArticleCreateRequest,
            @LoginUserEmail deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.addArticleAtFolder(id, request.name, request.url, deviceId)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "링크 삭제")
    @SecurityRequirement(name = "Device-Id")
    @DeleteMapping("/{id}/articles/{articleId}")
    fun createArticle(
            @PathVariable id: Long,
            @PathVariable articleId: Long,
            @LoginUserEmail deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.deleteArticleAtFolder(id, articleId, deviceId)
        return ResponseEntity.noContent().build()
    }
}
