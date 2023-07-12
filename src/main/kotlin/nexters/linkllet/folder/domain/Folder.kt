package nexters.linkllet.folder.domain

import nexters.linkllet.article.domain.Article
import nexters.linkllet.article.domain.Articles
import nexters.linkllet.common.domain.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "folder")
class Folder(

    @Column(name = "name", nullable = false)
    private var name: String,

    @Column(name = "member_id", nullable = false)
    private var memberId: Long = 0L,

    @Id
    @Column(name = "folder_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
) : BaseTimeEntity() {

    @Embedded
    private val articles: Articles = Articles()

    fun addArticle(article: Article) {
        this.articles.add(article)
    }

    fun addAllArticle(vararg article: Article) {
        this.articles.addAll(*article)
    }

    fun getArticles(): List<Article> {
        return articles.getArticles()
    }

    fun deleteArticle(article: Article) {
        this.articles.delete(article)
    }

    fun deleteAll() {
        this.articles.deleteAll()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Folder

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
