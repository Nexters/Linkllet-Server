package nexters.linkllet.folder.presentation

import nexters.linkllet.folder.dto.ArticleCreateRequest
import nexters.linkllet.folder.dto.FolderCreateRequest
import nexters.linkllet.folder.service.FolderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/folders")
class FolderCommandApi(
    private val folderService: FolderService,
) {

    companion object {
        private const val DEVICE_ID_HEADER_KEY: String = "Device-Id"
    }

    @PostMapping
    fun createFolder(
        @RequestBody request: FolderCreateRequest,
        @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.createFolder(request.name, deviceId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun deleteFolder(
        @PathVariable id: Long,
        @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.deleteFolder(id, deviceId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/articles")
    fun createArticle(
        @PathVariable id: Long,
        @RequestBody request: ArticleCreateRequest,
        @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.addArticleAtFolder(id, request.name, request.url, deviceId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}/articles/{articleId}")
    fun createArticle(
        @PathVariable id: Long,
        @PathVariable articleId: Long,
        @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.deleteArticleAtFolder(id, articleId, deviceId)
        return ResponseEntity.noContent().build()
    }
}
