package nexters.linkllet.member.service

import nexters.linkllet.member.domain.Member
import nexters.linkllet.member.domain.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun signUp(deviceId: String) {
        memberRepository.save(Member(deviceId))
    }
}
