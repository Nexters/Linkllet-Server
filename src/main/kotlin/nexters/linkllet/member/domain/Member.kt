package nexters.linkllet.member.domain

import nexters.linkllet.common.domain.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "member")
class Member(

    @Column(name = "device_id", nullable = false)
    private val deviceId: String,

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val _id: Long = 0L,
) : BaseTimeEntity() {

    val id: Long
        get() = this._id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        if (deviceId != other.deviceId) return false
        return _id == other._id
    }

    override fun hashCode(): Int {
        var result = deviceId.hashCode()
        result = 31 * result + _id.hashCode()
        return result
    }
}
