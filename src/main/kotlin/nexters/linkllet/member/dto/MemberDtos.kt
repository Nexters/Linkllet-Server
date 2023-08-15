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
