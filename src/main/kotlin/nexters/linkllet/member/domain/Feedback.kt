package nexters.linkllet.member.domain

import nexters.linkllet.common.domain.BaseTimeEntity
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(name = "feedback")
@Entity
class Feedback(

    @Column(name = "content", nullable = false)
    private val content: String,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "member_id")
    private val member: Member,

    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
) : BaseTimeEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Feedback

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
