package nexters.linkllet.article.domain

import nexters.linkllet.common.exception.dto.BadRequestException
import java.util.regex.Pattern
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Link(
    @Column(name = "link", nullable = false)
    private val _value: String,
) {

    init {
        if (!isValid(_value)) {
            throw BadRequestException("정상 URI가 아닙니다.")
        }
    }

    companion object {
        /**
         * Regular Expression by RFC 3986 for URI Validation
         * @link https://datatracker.ietf.org/doc/html/rfc3986#appendix-B
         * @author jiwoo
         */
        private const val REGEX_SCHEME = "[A-Za-z][+-.\\w^_]*:"

        // Example: "//".
        private const val REGEX_AUTHORATIVE_DECLARATION = "/{2}"

        // Optional component. Example: "suzie:abc123@". The use of the format "user:password" is deprecated.
        private const val REGEX_USERINFO = "(?:\\S+(?::\\S*)?@)?"

        // Examples: "fitbit.com", "22.231.113.64".
        private const val REGEX_HOST = "(?:" +  // @Author = http://www.regular-expressions.info/examples.html
                // IP address
                "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
                "|" +  // host name
                "(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)" +  // domain name
                "(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*" +  // TLD identifier must have >= 2 characters
                "(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))"

        // Example: ":8042".
        private const val REGEX_PORT = "(?::\\d{2,5})?"

        //Example: "/user/heartrate?foo=bar#element1".
        private const val REGEX_RESOURCE_PATH = "(?:/\\S*)?"

        private val REGEX_URL = "^(?:(?:" + REGEX_SCHEME + REGEX_AUTHORATIVE_DECLARATION + ")?" +
                REGEX_USERINFO + REGEX_HOST + REGEX_PORT + REGEX_RESOURCE_PATH + ")$"

        private val compiledLinkPattern = Pattern.compile(REGEX_URL)
    }

    val value: String
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
