package nexters.linkllet.common.exception

import nexters.linkllet.common.exception.dto.ErrorResponse
import nexters.linkllet.common.exception.dto.LinklletException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class CommonExceptionHandler {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!
    }

    @ExceptionHandler
    fun handleBadRequestException(ex: LinklletException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(ex.status).body(ErrorResponse(ex.message))
    }

    @ExceptionHandler
    fun unHandleException(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error("서버 에러 발생! ${request.method} ${request.requestURI} ${ex.message}")

        return ResponseEntity.internalServerError().body(ErrorResponse("서버 에러가 발생했습니다. 관리자에게 문의해주세요."))
    }
}
