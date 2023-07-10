package nexters.linkllet.domain

import javax.persistence.*

@Entity
@Table(name = "article")
class Article(

    _link: String,

    @Column(name = "title", nullable = false)
    var title: String,


    @Column(name = "member_id", nullable = false)
    val memberId: Long = 0L,

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
): BaseTimeEntity() {

    @Embedded
    private val link:Link

    init {
        this.link = Link(_link)
    }
}
