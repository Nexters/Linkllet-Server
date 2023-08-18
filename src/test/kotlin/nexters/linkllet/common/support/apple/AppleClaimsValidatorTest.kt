package nexters.linkllet.common.support.apple

import io.jsonwebtoken.Jwts
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class AppleClaimsValidatorTest {

    @Test
    fun `올바른 Claims 테스트`() {
        // given
        val appleOAuthConfigProperties = AppleOAuthConfigProperties()
        val appleClaimsValidator = AppleClaimsValidator(appleOAuthConfigProperties)
        val claimsMap: MutableMap<String, Any> = HashMap()
        claimsMap[NONCE_KEY] = AppleClaimsValidator.encrypt(appleOAuthConfigProperties.nonce)

        val claims = Jwts.claims(claimsMap)
                .setIssuer(appleOAuthConfigProperties.iss)
                .setAudience(appleOAuthConfigProperties.clientId)

        // when
        // then
        assertThat(appleClaimsValidator.isValid(claims)).isTrue
    }

    companion object {
        private const val NONCE_KEY = "nonce"
    }

    @ParameterizedTest
    @CsvSource(
        "invalid, iss, aud",
        "nonce, invalid, aud",
        "nonce, iss, invalid"
    )
    fun `잘못된 claims 테스트`(nonce: String, iss: String, aud: String) {
        // given
        val appleOAuthConfigProperties = AppleOAuthConfigProperties()
        val appleClaimsValidator = AppleClaimsValidator(appleOAuthConfigProperties)
        val claimsMap: MutableMap<String, Any> = HashMap()
        claimsMap[NONCE_KEY] = AppleClaimsValidator.encrypt(nonce)

        val claims = Jwts.claims(claimsMap)
                .setIssuer(iss)
                .setAudience(aud)

        // when
        // then
        assertThat(appleClaimsValidator.isValid(claims)).isFalse
    }
}
