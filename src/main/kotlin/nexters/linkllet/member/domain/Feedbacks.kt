package nexters.linkllet.member.domain

import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
class Feedbacks {

    @OneToMany(mappedBy = "member", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    private val feedbacks: MutableList<Feedback> = ArrayList()

    fun add(feedback: Feedback) {
        this.feedbacks.add(feedback)
    }

    fun getAllFeedback(): List<Feedback> {
        if (this.feedbacks.isEmpty()) return emptyList()

        return this.feedbacks.toList()
    }
}
