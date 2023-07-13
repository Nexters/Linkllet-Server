package nexters.linkllet.util

import nexters.linkllet.member.domain.Member
import nexters.linkllet.member.domain.MemberRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DatabaseLoader(
    private val memberRepository: MemberRepository,
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!
    }

    fun loadData() {
        log.debug("[call DataLoader]")

        memberRepository.save(Member("device_id"))

        log.debug("[init complete DataLoader]")
    }
}
