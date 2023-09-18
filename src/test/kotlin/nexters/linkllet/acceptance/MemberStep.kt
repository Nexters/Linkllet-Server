package nexters.linkllet.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nexters.linkllet.member.dto.LoginRequest
import nexters.linkllet.member.dto.LoginResponse
import nexters.linkllet.member.dto.MemberFeedbackRequest
import nexters.linkllet.member.dto.MemberSignUpRequest
import org.springframework.http.MediaType

class MemberStep {

    companion object {

        fun 회원_가입_요청(request: MemberSignUpRequest): ExtractableResponse<Response> =
                RestAssured.given().log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(request)
                        .`when`().post("/api/v1/members")
                        .then().log().all()
                        .extract()

        fun 로그인(request: LoginRequest): String =
                RestAssured.given().log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(request)
                        .`when`().post("/api/v1/members/login")
                        .then().log().all()
                        .extract()
                        .`as`(LoginResponse::class.java)
                        .token


        fun 피드백_요청(token: String, feedback: String): ExtractableResponse<Response> =
                RestAssured.given().log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .auth().oauth2(token)
                        .body(MemberFeedbackRequest(feedback))
                        .`when`().post("/api/v1/members/feedbacks")
                        .then().log().all()
                        .extract()
    }
}
