package nexters.linkllet.member.domain

import javax.persistence.*

@Entity
@Table(name = "member")
class Member(

    @Column(name = "device_id", nullable = false)
    private val deviceId: String,

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
)
