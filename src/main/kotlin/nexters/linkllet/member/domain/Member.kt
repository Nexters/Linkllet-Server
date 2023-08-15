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

    /*
     * OAuth 최종 완료 후, deviceId 요청이 없다고 판단되면 삭제할 것
     */
    @Column(name = "device_id")
    private val deviceId: String? = null,

    @Column(name = "email", length = 100, nullable = false)
    private val email: String,

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
