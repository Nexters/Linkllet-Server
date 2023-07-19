package nexters.linkllet.member.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import nexters.linkllet.member.dto.MemberSignUpRequest
import nexters.linkllet.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Members", description = "회원")
@RestController
@RequestMapping("/api/v1")
class MemberCommandApi(
    private val memberService: MemberService,
) {

    @Operation(summary = "회원가입")
    @PostMapping("/members")
    fun signUp(
        @RequestBody request: MemberSignUpRequest,
    ): ResponseEntity<Unit> {
        memberService.signUp(request.deviceId)
        return ResponseEntity.ok().build()
    }
}
