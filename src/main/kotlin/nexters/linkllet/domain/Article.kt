package nexters.linkllet.domain

import javax.persistence.*

@Entity
@Table(name = "article")
class Article(

    _link: String,

    var name: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
): BaseTimeEntity() {

    @Embedded
    private val link:Link

    init {
        this.link = Link(_link)
    }
}
