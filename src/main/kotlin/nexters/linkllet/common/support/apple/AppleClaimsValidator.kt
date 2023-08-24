package nexters.linkllet.common.support.apple

import io.jsonwebtoken.Claims
import nexters.linkllet.config.AppleOAuthConfigProperties
import org.springframework.stereotype.Component

@Component
class AppleClaimsValidator(
    private val appleOAuthConfigProperties: AppleOAuthConfigProperties,
) {

    fun isValid(claims: Claims): Boolean {
        return claims.issuer.contains(appleOAuthConfigProperties.iss) &&
                claims.audience == appleOAuthConfigProperties.clientId
    }
}
