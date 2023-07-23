package nexters.linkllet.domain.member

import nexters.linkllet.member.domain.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MemberTest {

    @DisplayName("피드백을 추가할 수 있다")
    @Test
    fun add_feedback() {
        // given
        val member = Member("shine")

        // when
        member.addFeedback("피드백1")
        member.addFeedback("피드백2")

        // then
        assertThat(member.getAllFeedback()).hasSize(2)
    }
}
