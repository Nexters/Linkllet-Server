package nexters.linkllet.article.domain

import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
class Articles {

    @OneToMany(mappedBy = "folder", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    private val articles: MutableList<Article> = mutableListOf()

    companion object {
        private val MAX_FOLDER_SIZE = 50
    }

    fun add(article: Article) {
        if (!isValidSize()) throw IllegalStateException("폴더가 가득 찼습니다.")

        this.articles.add(article)
    }

    fun addAll(vararg article: Article) {
        if (!isValidSize()) throw IllegalStateException("폴더가 가득 찼습니다.")

        this.articles.addAll(article)
    }

    fun getArticles(): List<Article> {
        if (this.articles.isEmpty()) return emptyList()

        return this.articles.toList()
    }

    fun delete(article: Article) {
        this.articles.remove(article)
    }

    fun deleteAll() {
        this.articles.clear()
    }

    private fun isValidSize() = this.articles.size < MAX_FOLDER_SIZE
}
