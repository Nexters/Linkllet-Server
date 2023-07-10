package nexters.linkllet.controller

import nexters.linkllet.dto.MemberSignUpRequest
import nexters.linkllet.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class MemberController(
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
