package nexters.linkllet.member.domain

import nexters.linkllet.common.exception.dto.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository

fun MemberRepository.findByDeviceIdOrThrow(deviceId: String): Member {
    return findByDeviceId(deviceId) ?: throw NotFoundException("회원이 없어요")
}

fun MemberRepository.findByEmailOrThrow(email: String): Member {
    return findByEmail(email) ?: throw NotFoundException("회원이 없어요")
}

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByDeviceId(deviceId: String): Member?

    fun findByEmail(email: String): Member?
}
