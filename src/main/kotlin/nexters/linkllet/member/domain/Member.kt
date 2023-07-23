package nexters.linkllet.member.domain

import nexters.linkllet.common.domain.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "member")
class Member(

    @Column(name = "device_id", nullable = false)
    private val deviceId: String,

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
) : BaseTimeEntity() {

    @Embedded
    private val feedbacks: Feedbacks = Feedbacks()

    val getId: Long
        get() = this.id

    fun addFeedback(feedback: String) {
        this.feedbacks.add(Feedback(feedback, this))
    }

    fun getAllFeedback(): List<Feedback> {
        return feedbacks.getAllFeedback()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        if (deviceId != other.deviceId) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        var result = deviceId.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
