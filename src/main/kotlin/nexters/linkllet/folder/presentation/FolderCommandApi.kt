package nexters.linkllet.folder.presentation

import nexters.linkllet.folder.dto.FolderCreateRequest
import nexters.linkllet.folder.service.FolderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class FolderCommandApi(
    private val folderService: FolderService,
) {

    companion object {
        private const val DEVICE_ID_HEADER_KEY: String = "Device-Id"
    }

    @PostMapping("/folders")
    fun create(
        @RequestBody request: FolderCreateRequest,
        @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.createFolder(request.name, deviceId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/folders/{id}")
    fun delete(
        @PathVariable id: Long,
        @RequestHeader(DEVICE_ID_HEADER_KEY) deviceId: String,
    ): ResponseEntity<Unit> {
        folderService.deleteFolder(id, deviceId)
        return ResponseEntity.noContent().build()
    }
}
