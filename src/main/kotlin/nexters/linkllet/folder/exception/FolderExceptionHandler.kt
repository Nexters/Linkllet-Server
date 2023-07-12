package nexters.linkllet.folder.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class FolderExceptionHandler {

    @ExceptionHandler
    fun IllegalStateExceptionHandler(ex: IllegalStateException): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(ex.message)
    }
}
