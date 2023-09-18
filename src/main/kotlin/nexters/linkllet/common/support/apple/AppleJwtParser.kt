package nexters.linkllet.common.support.apple

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import java.security.PublicKey
import nexters.linkllet.common.exception.dto.UnauthorizedException
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils

@Component
class AppleJwtParser {

    fun parseHeaders(idToken: String): Map<String, String> {
        try {
            val encodedHeader = idToken.split(IDENTITY_TOKEN_VALUE_DELIMITER)[HEADER_INDEX]
            /*
            * encodedHeader.replace(".", "") 이유는 아래 에러 발생 때문
            * illegal base64 character 2e
            * (ASCII 2e == 46 : ".")
            * Java 에서는 "." 안없애도 에러가 안뜨지만 kotlin 은 에러 발생. 왜 발생하는 것인지 추후 확인 필요
            */
            val decodedHeader =
                String(Base64Utils.decodeFromUrlSafeString(encodedHeader.replace(".", "")))
            return OBJECT_MAPPER.readValue(decodedHeader)
        } catch (e: JsonProcessingException) {
            throw UnauthorizedException("Apple OAuth Identity Token 형식이 올바르지 않습니다.")
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw UnauthorizedException("Apple OAuth Identity Token 형식이 올바르지 않습니다.")
        }
    }

    fun parsePublicKeyAndGetClaims(idToken: String, publicKey: PublicKey): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(idToken)
                .body
        } catch (e: ExpiredJwtException) {
            throw UnauthorizedException("Apple OAuth 로그인 중 Identity Token 유효기간이 만료됐습니다.")
        } catch (e: UnsupportedJwtException) {
            throw UnauthorizedException("Apple OAuth Identity Token 값이 올바르지 않습니다.")
        } catch (e: MalformedJwtException) {
            throw UnauthorizedException("Apple OAuth Identity Token 값이 올바르지 않습니다.")
        } catch (e: SignatureException) {
            throw UnauthorizedException("Apple OAuth Identity Token 값이 올바르지 않습니다.")
        } catch (e: IllegalArgumentException) {
            throw UnauthorizedException("Apple OAuth Identity Token 값이 올바르지 않습니다.")
        }
    }

    companion object {
        private const val IDENTITY_TOKEN_VALUE_DELIMITER = "\\."
        private const val HEADER_INDEX = 0
        private val OBJECT_MAPPER: ObjectMapper = ObjectMapper()
    }
}
