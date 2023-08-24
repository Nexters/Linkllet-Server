package nexters.linkllet.common.support

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import nexters.linkllet.common.exception.dto.UnauthorizedException
import nexters.linkllet.config.JwtConfigProperties
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider(
    private val jwtConfigProperties: JwtConfigProperties,
) {
    private val signingKey: SecretKey =
        Keys.hmacShaKeyFor(jwtConfigProperties.secretKey.toByteArray(StandardCharsets.UTF_8))

    fun generateToken(payload: String): String {
        val claims: Claims = Jwts.claims().setSubject(payload)
        val now = Date()
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + jwtConfigProperties.expireLength))
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
