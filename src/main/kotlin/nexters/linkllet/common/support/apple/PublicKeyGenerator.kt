package nexters.linkllet.common.support.apple

import java.math.BigInteger
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPublicKeySpec
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils

@Component
class PublicKeyGenerator {

    fun generatePublicKey(headers: Map<String, String>, applePublicKeys: ApplePublicKeys): PublicKey {
        val applePublicKey =
                applePublicKeys.getMatchesKey(headers[SIGN_ALGORITHM_HEADER_KEY], headers[KEY_ID_HEADER_KEY])
        return generatePublicKeyWithApplePublicKey(applePublicKey)
    }

    private fun generatePublicKeyWithApplePublicKey(publicKey: ApplePublicKey): PublicKey {
        val nBytes = Base64Utils.decodeFromUrlSafeString(publicKey.n)
        val eBytes = Base64Utils.decodeFromUrlSafeString(publicKey.e)

        val n = BigInteger(POSITIVE_SIGN_NUMBER, nBytes)
        val e = BigInteger(POSITIVE_SIGN_NUMBER, eBytes)

        val publicKeySpec = RSAPublicKeySpec(n, e)

        return try {
            val keyFactory: KeyFactory = KeyFactory.getInstance(publicKey.kty)
            keyFactory.generatePublic(publicKeySpec)
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
        } catch (e: InvalidKeySpecException) {
            throw IllegalStateException("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
        }
    }

    companion object {
        private const val SIGN_ALGORITHM_HEADER_KEY = "alg"
        private const val KEY_ID_HEADER_KEY = "kid"
        private const val POSITIVE_SIGN_NUMBER = 1
    }
}
