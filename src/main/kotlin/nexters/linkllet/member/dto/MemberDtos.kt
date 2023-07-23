package nexters.linkllet.member.dto

data class MemberSignUpRequest(
    val deviceId: String,
)

data class MemberFeedbackRequest(
    val feedback: String,
)
