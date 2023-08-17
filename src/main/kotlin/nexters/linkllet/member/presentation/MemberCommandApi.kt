package nexters.linkllet.member.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import nexters.linkllet.common.support.LoginUserEmail
import nexters.linkllet.member.dto.AppleLoginRequest
import nexters.linkllet.member.dto.AppleLoginResponse
import nexters.linkllet.member.dto.LoginRequest
import nexters.linkllet.member.dto.LoginResponse
import nexters.linkllet.member.dto.MemberFeedbackRequest
import nexters.linkllet.member.dto.MemberSignUpRequest
import nexters.linkllet.member.service.AuthService
import nexters.linkllet.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Members", description = "회원")
@RestController
@RequestMapping("/api/v1/members")
class MemberCommandApi(
        private val memberService: MemberService,
        private val authService: AuthService,
) {

    @Operation(summary = "회원가입")
    @PostMapping
    fun signUp(
            @RequestBody request: MemberSignUpRequest,
    ): ResponseEntity<Unit> {
        memberService.signUp(request.email)
        return ResponseEntity.ok().build()
    }

    /*
     * OAuth 최종 완료 되면 지울 것, 토큰 테스트용 API
     */
    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(
            @RequestBody request: LoginRequest,
    ): ResponseEntity<LoginResponse> {
        val response = authService.login(request)
        return ResponseEntity.ok().body(response)
    }

    @Operation(summary = "Apple OAuth 로그인")
    @PostMapping("/login/apple")
    fun loginApple(
            @RequestBody request: AppleLoginRequest,
    ): ResponseEntity<AppleLoginResponse> {
        val response = authService.loginApple(request)
        return ResponseEntity.ok().body(response)
    }

    @Operation(summary = "피드백 생성")
    @SecurityRequirement(name = "Device-Id")
    @PostMapping("/feedbacks")
    fun addFeedback(
            @RequestBody request: MemberFeedbackRequest,
            @LoginUserEmail email: String,
    ): ResponseEntity<Unit> {
        memberService.addFeedback(email, request.feedback)
        return ResponseEntity.ok().build()
    }
}
