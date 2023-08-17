package nexters.linkllet.common.support.apple

import io.jsonwebtoken.Claims
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import org.springframework.stereotype.Component

@Component
class AppleClaimsValidator(
        private val appleOAuthConfigProperties: AppleOAuthConfigProperties,
) {

    fun isValid(claims: Claims): Boolean {
        return claims.issuer.contains(appleOAuthConfigProperties.iss) &&
                claims.audience == appleOAuthConfigProperties.clientId &&
                claims.get(NONCE_KEY, String::class.java) == encrypt(appleOAuthConfigProperties.nonce);
    }

    companion object {
        fun encrypt(nonce: String): String {
            return try {
                val sha256 = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM)
                val digest = sha256.digest(nonce.toByteArray(StandardCharsets.UTF_8))
                val hexString = StringBuilder()
                for (b in digest) {
                    hexString.append(String.format(HEX_STRING_FORMAT, b))
                }
                hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                throw IllegalArgumentException("Apple OAuth 통신 암호화 과정 중 문제가 발생했습니다.")
            }
        }

        private const val MESSAGE_DIGEST_ALGORITHM = "SHA-256"
        private const val HEX_STRING_FORMAT = "%02x"

        private const val NONCE_KEY = "nonce"
    }
}
