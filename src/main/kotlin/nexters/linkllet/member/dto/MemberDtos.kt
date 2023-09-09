package nexters.linkllet.member.dto

data class MemberSignUpRequest(
        val deviceId: String,
)

data class MemberFeedbackRequest(
        val feedback: String,
)

data class LoginRequest(
        val deviceId: String,
)

data class LoginResponse(
        val token: String,
)

data class AppleLoginRequest(
        val token: String,
)

data class OAuthLoginResponse(
        val token: String,
)
