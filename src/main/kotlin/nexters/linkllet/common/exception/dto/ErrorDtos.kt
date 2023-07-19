package nexters.linkllet.common.exception.dto

import org.springframework.http.HttpStatus

data class ErrorResponse(
        val message: String?,
)

open class LinklletException(override val message: String, val status: Int) : RuntimeException()

class BadRequestException(message: String = "잘못된 요청입니다.")
    : LinklletException(message, HttpStatus.BAD_REQUEST.value())

class UnauthorizedException(message: String = "인증되지 않은 사용자입니다.")
    : LinklletException(message, HttpStatus.UNAUTHORIZED.value())

class ForbiddenException(message: String = "권한이 존재하지 않습니다.")
    : LinklletException(message, HttpStatus.FORBIDDEN.value())

class NotFoundException(message: String = "존재하지 않는 요청입니다.")
    : LinklletException(message, HttpStatus.NOT_FOUND.value())

class ConflictException(message: String = "이미 등록된 디바이스 입니다.")
    : LinklletException(message, HttpStatus.CONFLICT.value())
