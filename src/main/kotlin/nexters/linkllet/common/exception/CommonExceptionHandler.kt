package nexters.linkllet.common.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonExceptionHandler {

    @ExceptionHandler
    fun IllegalStateExceptionHandler(ex: IllegalStateException): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(ex.message)
    }
}
