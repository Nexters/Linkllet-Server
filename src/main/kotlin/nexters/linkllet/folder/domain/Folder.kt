package nexters.linkllet.folder.domain

import nexters.linkllet.article.domain.Article
import nexters.linkllet.article.domain.Articles
import nexters.linkllet.common.domain.BaseTimeEntity
import nexters.linkllet.common.exception.dto.BadRequestException
import javax.persistence.*

@Entity
@Table(name = "folder")
class Folder(

        @Column(name = "name", nullable = false)
        var name: String,

        @Column(name = "member_id", nullable = false)
        private var memberId: Long = 0L,

        @Enumerated(EnumType.STRING)
        @Column(name = "type", nullable = false)
        private val type: FolderType = FolderType.PERSONALIZED,

        @Id
        @Column(name = "folder_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private val id: Long = 0L,
) : BaseTimeEntity() {

    @Embedded
    private val articles: Articles = Articles()

    val getId: Long
        get() = this.id

    val getType: FolderType
        get() = this.type

    fun addArticle(article: Article) {
        if (!this.isFolderOwnerId(article.getMemberId)) {
            throw BadRequestException("본인의 폴더가 아닙니다")
        }

        this.articles.add(article)
    }

    fun addAllArticle(vararg article: Article) {
        this.articles.addAll(*article)
    }

    fun getArticles(): List<Article> {
        return articles.getArticles()
    }

    fun deleteArticleById(articleId: Long, memberId: Long) {
        if (!this.isFolderOwnerId(memberId)) {
            throw BadRequestException("본인의 폴더가 아닙니다")
        }

        this.articles.deleteById(articleId)
    }

    fun deleteArticle(article: Article) {
        this.articles.delete(article)
    }

    fun deleteAll() {
        this.articles.deleteAll()
    }

    fun isFolderOwnerId(memberId: Long): Boolean {
        return this.memberId == memberId
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
