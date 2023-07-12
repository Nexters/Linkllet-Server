package nexters.linkllet.article.domain

import java.lang.IllegalArgumentException
import java.util.regex.Pattern
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Link(
    @Column(name = "link", nullable = false)
    private val _value: String
) {

    init {
        if(!isValid(_value)) {
            throw IllegalArgumentException("정상 URI가 아닙니다.")
        }
    }

    companion object{
        /**
         * Regular Expression by RFC 2396 for URI Validation
         * @link https://www.rfc-editor.org/rfc/rfc2396#appendix-B
         * @author jiwoo
         */
        private val linkPattern: String = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"

        private val compiledLinkPattern = Pattern.compile(linkPattern)
    }

    val value:String
        get() = this._value

    private fun isValid(link: String): Boolean {
        return patternMatches(link)
    }

    private fun patternMatches(link: String): Boolean {
        return compiledLinkPattern
            .matcher(link)
            .matches()
    }
}
