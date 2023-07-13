package nexters.linkllet.article.domain

import nexters.linkllet.common.domain.BaseTimeEntity
import nexters.linkllet.folder.domain.Folder
import javax.persistence.*

@Entity
@Table(name = "article")
class Article(

    _link: String,

    @Column(name = "title", nullable = false)
    private var title: String,

    @Column(name = "member_id", nullable = false)
    private val memberId: Long = 0L,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "folder_id")
    private val folder: Folder? = null,

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
) : BaseTimeEntity() {

    @Embedded
    private val link: Link

    init {
        this.link = Link(_link)
    }

    val getId: Long
        get() = this.id

    val getTitle: String
        get() = this.title

    val getLink: String
        get() = this.link.value

    val getMemberId: Long
        get() = this.memberId

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Article

        if (title != other.title) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
