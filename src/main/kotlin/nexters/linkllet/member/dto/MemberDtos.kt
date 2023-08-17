package nexters.linkllet.member.dto

data class MemberSignUpRequest(
        val email: String,
)

data class MemberFeedbackRequest(
        val feedback: String,
)

data class LoginRequest(
        val email: String,
)

data class LoginResponse(
        val token: String,
)

data class AppleLoginRequest(
        val token: String,
)

/*
 * Apple, Kakao 둘 다 LoginResponse 로 통합할지,
 * 아니면 Apple, Kakao 별도의 res DTO 만들지 고민됨
 */
data class AppleLoginResponse(
        val token: String,
)
