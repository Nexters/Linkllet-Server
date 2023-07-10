package nexters.linkllet.domain

import javax.persistence.*

@Entity
@Table(name = "tag")
class Tag(

    @Column(name = "name", nullable = false)
    val name:String,

    @Column(name = "article_id", nullable = false)
    val articleId: Long = 0L,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
): BaseTimeEntity()
