package nexters.linkllet.article.domain

import javax.persistence.CascadeType
import javax.persistence.Embeddable
import javax.persistence.OneToMany

@Embeddable
class Articles {

    @OneToMany(mappedBy = "folder", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    private val articles: MutableList<Article> = mutableListOf()

    fun add(article: Article) {
        this.articles.add(article)
    }

    fun addAll(vararg article: Article) {
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
}
