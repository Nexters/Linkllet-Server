package nexters.linkllet.folder.presentation

import nexters.linkllet.common.support.AccessDeviceId
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

    @PostMapping
    fun createFolder(
            @RequestBody request: FolderCreateRequest,
            @AccessDeviceId deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.createFolder(request.name, deviceId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun deleteFolder(
            @PathVariable id: Long,
            @AccessDeviceId deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.deleteFolder(id, deviceId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/articles")
    fun createArticle(
            @PathVariable id: Long,
            @RequestBody request: ArticleCreateRequest,
            @AccessDeviceId deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.addArticleAtFolder(id, request.name, request.url, deviceId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}/articles/{articleId}")
    fun createArticle(
            @PathVariable id: Long,
            @PathVariable articleId: Long,
            @AccessDeviceId deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.deleteArticleAtFolder(id, articleId, deviceId)
        return ResponseEntity.noContent().build()
    }
}
