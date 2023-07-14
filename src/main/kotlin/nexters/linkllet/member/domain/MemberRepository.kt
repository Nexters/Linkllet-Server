package nexters.linkllet.member.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun MemberRepository.findByIdOrThrow(memberId: Long): Member {
    return findByIdOrNull(memberId)
        ?: throw IllegalArgumentException("회원이 없어요")
}

fun MemberRepository.findByDeviceIdOrThrow(deviceId: String): Member {
    return findByDeviceId(deviceId) ?: throw IllegalArgumentException("회원이 없어요")
}

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByDeviceId(deviceId: String): Member?
}
