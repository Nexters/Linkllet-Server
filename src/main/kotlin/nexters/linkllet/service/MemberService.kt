package nexters.linkllet.service

import nexters.linkllet.domain.Member
import nexters.linkllet.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
        private val memberRepository: MemberRepository,
) {

    fun signUp(deviceId: String) {
        memberRepository.save(Member(deviceId))
    }
}
