package nexters.linkllet.member.service

import nexters.linkllet.common.support.JwtProvider
import nexters.linkllet.common.support.apple.AppleOAuthUserProvider
import nexters.linkllet.member.dto.AppleLoginRequest
import nexters.linkllet.member.dto.AppleLoginResponse
import nexters.linkllet.member.dto.LoginRequest
import nexters.linkllet.member.dto.LoginResponse
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val jwtTokenProvider: JwtProvider,
        private val appleOAuthUserProvider: AppleOAuthUserProvider,
) {

    fun login(request: LoginRequest): LoginResponse {
        val token = jwtTokenProvider.generateToken(request.email)
        return LoginResponse(token)
    }

    fun loginApple(request: AppleLoginRequest): AppleLoginResponse {
        val applePlatformUser = appleOAuthUserProvider.getApplePlatformUser(request.token)
        val token = jwtTokenProvider.generateToken(applePlatformUser.email)
        return AppleLoginResponse(token)
    }
}
