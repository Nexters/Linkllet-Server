package nexters.linkllet.member.presentation

import nexters.linkllet.member.dto.MemberSignUpRequest
import nexters.linkllet.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class MemberCommandApi(
    private val memberService: MemberService,
) {

    @PostMapping("/members")
    fun signUp(
        @RequestBody request: MemberSignUpRequest,
    ): ResponseEntity<Unit> {
        memberService.signUp(request.deviceId)
        return ResponseEntity.ok().build()
    }
}
