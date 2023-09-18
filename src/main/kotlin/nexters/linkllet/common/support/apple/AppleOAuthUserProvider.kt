package nexters.linkllet.common.support.apple

import io.jsonwebtoken.Claims
import nexters.linkllet.common.exception.dto.UnauthorizedException
import org.springframework.stereotype.Component

@Component
class AppleOAuthUserProvider(
        private val appleJwtParser: AppleJwtParser,
        private val appleClient: AppleClient,
        private val publicKeyGenerator: PublicKeyGenerator,
        private val appleClaimsValidator: AppleClaimsValidator,
) {

    fun getApplePlatformUser(idToken: String): ApplePlatformUser {
        val headers = appleJwtParser.parseHeaders(idToken)
        val applePublicKeys = appleClient.getApplePublicKeys()

        val publicKey = publicKeyGenerator.generatePublicKey(headers, applePublicKeys)
        val claims = appleJwtParser.parsePublicKeyAndGetClaims(idToken, publicKey)
        validateClaims(claims)
        return ApplePlatformUser(claims.subject, claims["email"].toString())
    }

    private fun validateClaims(claims: Claims) {
        if (!appleClaimsValidator.isValid(claims)) {
            throw UnauthorizedException("Apple OAuth Claims 값이 올바르지 않습니다.")
        }
    }
}
