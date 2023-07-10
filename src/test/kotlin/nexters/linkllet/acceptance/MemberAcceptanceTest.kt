package nexters.linkllet.acceptance

import io.restassured.RestAssured
import nexters.linkllet.dto.MemberSignUpRequest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType


class MemberAcceptanceTest : AcceptanceTest() {

    @Test
    fun `회원 가입`() {
        val request = MemberSignUpRequest("kth990303")

        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .`when`().post("/api/v1/members")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
    }
}
