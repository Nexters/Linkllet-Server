package nexters.linkllet.acceptance

import nexters.linkllet.acceptance.CommonStep.Companion.응답_확인
import nexters.linkllet.acceptance.MemberStep.Companion.회원_가입_요청
import nexters.linkllet.member.dto.MemberSignUpRequest
import nexters.linkllet.util.AcceptanceTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus


@DisplayName("인수 : Member")
class MemberAcceptanceTest : AcceptanceTest() {

    @Test
    fun `회원 가입`() {
        val 회원_가입_정보 = MemberSignUpRequest("kth990303")

        val 회원_가입_응답 = 회원_가입_요청(회원_가입_정보)

        응답_확인(회원_가입_응답, HttpStatus.OK)
    }
}
