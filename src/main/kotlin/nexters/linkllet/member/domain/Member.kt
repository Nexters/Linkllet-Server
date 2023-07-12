package nexters.linkllet.member.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "member")
class Member(

    @Column(name = "device_id", nullable = false)
    val deviceId: String,

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) {
}
