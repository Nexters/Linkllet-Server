package nexters.linkllet.common.support

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey
import nexters.linkllet.common.exception.dto.UnauthorizedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtProvider(
        @Value("\${security.jwt.token.secret-key}") val secretKey: String,
        @Value("\${security.jwt.token.expire-length}") val expirationTime: Long,
) {
    private val signingKey: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    fun generateToken(payload: String): String {
        val claims: Claims = Jwts.claims().setSubject(payload)
        val now = Date()
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + expirationTime))
                .signWith(signingKey)
                .compact()
    }

    fun getPayload(token: String): String {
        try {
            return extractPayload(token)
        } catch (e: JwtException) {
            throw UnauthorizedException()
        } catch (e: IllegalArgumentException) {
            throw UnauthorizedException()
        }
    }

    private fun extractPayload(token: String) = Jwts.parserBuilder()
            .setSigningKey(signingKey.encoded)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
}
