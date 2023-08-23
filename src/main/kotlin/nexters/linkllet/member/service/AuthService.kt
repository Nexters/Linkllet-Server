package nexters.linkllet.member.service

import nexters.linkllet.common.support.JwtProvider
import nexters.linkllet.common.support.apple.AppleOAuthUserProvider
import nexters.linkllet.common.support.kakao.KakaoOAuthUserProvider
import nexters.linkllet.folder.domain.Folder
import nexters.linkllet.folder.domain.FolderRepository
import nexters.linkllet.folder.domain.FolderType
import nexters.linkllet.member.domain.Member
import nexters.linkllet.member.domain.MemberRepository
import nexters.linkllet.member.dto.AppleLoginRequest
import nexters.linkllet.member.dto.LoginRequest
import nexters.linkllet.member.dto.LoginResponse
import nexters.linkllet.member.dto.OAuthLoginResponse
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtTokenProvider: JwtProvider,
    private val appleOAuthUserProvider: AppleOAuthUserProvider,
    private val kakaoOAuthUserProvider: KakaoOAuthUserProvider,
    private val memberRepository: MemberRepository,
    private val folderRepository: FolderRepository,
) {

    fun login(request: LoginRequest): LoginResponse {
        val token = jwtTokenProvider.generateToken(request.email)
        return LoginResponse(token)
    }

    fun loginApple(request: AppleLoginRequest): OAuthLoginResponse {
        val applePlatformUser = appleOAuthUserProvider.getApplePlatformUser(request.token)
        initMember(applePlatformUser.email)
        val token = jwtTokenProvider.generateToken(applePlatformUser.email)
        return OAuthLoginResponse(token)
    }

    fun loginKakao(authorizationCode: String): OAuthLoginResponse {
        val kakaoPlatformUser = kakaoOAuthUserProvider.getKakaoPlatformUser(authorizationCode)
        initMember(kakaoPlatformUser.getEmail())
        val token = jwtTokenProvider.generateToken(kakaoPlatformUser.getEmail())
        return OAuthLoginResponse(token)
    }

    private fun initMember(email: String) {
        val newMember = memberRepository.save(Member(email = email))
        createStartFolder(newMember)
    }

    private fun createStartFolder(newMember: Member) {
        folderRepository.saveAll(
            listOf(
                Folder(DEFAULT_FOLDER_NAME, newMember.getId, FolderType.DEFAULT),
                Folder("Folder 1", newMember.getId, FolderType.PERSONALIZED),
                Folder("Folder 2", newMember.getId, FolderType.PERSONALIZED)
            )
        )
    }
}
