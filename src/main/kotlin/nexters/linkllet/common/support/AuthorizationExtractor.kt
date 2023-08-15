package nexters.linkllet.common.support

import javax.servlet.http.HttpServletRequest
import nexters.linkllet.common.exception.dto.UnauthorizedException

class AuthorizationExtractor {

    companion object {

        private const val AUTHENTICATION_TYPE = "Bearer"
        private const val AUTHORIZATION_HEADER_KEY = "Authorization"
        private const val START_TOKEN_INDEX = 6

        fun extractToken(request: HttpServletRequest): String {
            val headers = request.getHeaders(AUTHORIZATION_HEADER_KEY)
            while (headers.hasMoreElements()) {
                val value = headers.nextElement()

                if (validateStartsAuthorizationKey(value)) {
                    val extractToken = value.trim().substring(START_TOKEN_INDEX).trim()
                    validateExtractToken(extractToken)
                    return extractToken
                }
            }
            throw UnauthorizedException()
        }

        private fun validateStartsAuthorizationKey(value: String) =
                value.lowercase().startsWith(AUTHENTICATION_TYPE.lowercase())

        private fun validateExtractToken(extractToken: String) {
            if (extractToken.isBlank()) {
                throw UnauthorizedException("토큰은 공백일 수 없습니다.")
            }
        }
    }
}
