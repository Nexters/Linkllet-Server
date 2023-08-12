package nexters.linkllet.member.service

import nexters.linkllet.common.support.JwtProvider
import nexters.linkllet.member.dto.LoginRequest
import nexters.linkllet.member.dto.LoginResponse
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val jwtTokenProvider: JwtProvider,
) {

    fun login(request: LoginRequest): LoginResponse {
        val token = jwtTokenProvider.generateToken(request.email)
        return LoginResponse(token)
    }
}
