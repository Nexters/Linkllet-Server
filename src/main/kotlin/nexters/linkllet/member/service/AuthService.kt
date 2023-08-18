package nexters.linkllet.member.service

import nexters.linkllet.common.support.JwtProvider
import nexters.linkllet.common.support.apple.AppleOAuthUserProvider
import nexters.linkllet.common.support.kakao.KakaoOAuthUserProvider
import nexters.linkllet.member.domain.Member
import nexters.linkllet.member.domain.MemberRepository
import nexters.linkllet.member.dto.AppleLoginRequest
import nexters.linkllet.member.dto.OAuthLoginResponse
import nexters.linkllet.member.dto.LoginRequest
import nexters.linkllet.member.dto.LoginResponse
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val jwtTokenProvider: JwtProvider,
        private val appleOAuthUserProvider: AppleOAuthUserProvider,
        private val kakaoOAuthUserProvider: KakaoOAuthUserProvider,
        private val memberRepository: MemberRepository,
) {

    fun login(request: LoginRequest): LoginResponse {
        val token = jwtTokenProvider.generateToken(request.email)
        return LoginResponse(token)
    }

    fun loginApple(request: AppleLoginRequest): OAuthLoginResponse {
        val applePlatformUser = appleOAuthUserProvider.getApplePlatformUser(request.token)
        memberRepository.save(Member(email = applePlatformUser.email))
        val token = jwtTokenProvider.generateToken(applePlatformUser.email)
        return OAuthLoginResponse(token)
    }

    fun loginKakao(authorizationCode: String): OAuthLoginResponse {
        val kakaoPlatformUser = kakaoOAuthUserProvider.getKakaoPlatformUser(authorizationCode)
        memberRepository.save(Member(email = kakaoPlatformUser.getEmail()))
        val token = jwtTokenProvider.generateToken(kakaoPlatformUser.getEmail())
        return OAuthLoginResponse(token)
    }
}
