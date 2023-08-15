package nexters.linkllet.common.support

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import nexters.linkllet.common.exception.dto.UnauthorizedException
import nexters.linkllet.config.JwtConfigProperties
import org.springframework.stereotype.Component

@Component
class JwtProvider(
        private val jwtConfigProperties: JwtConfigProperties,
) {

    fun generateToken(payload: String): String {
        val claims: Claims = Jwts.claims().setSubject(payload)
        val now = Date()
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + jwtConfigProperties.expirationTime))
                .signWith(SignatureAlgorithm.HS256, jwtConfigProperties.secretKey)
                .compact()
    }

    fun getPayload(token: String): String {
        try {
            return Jwts.parser().parseClaimsJws(token).body.subject
        } catch (e: JwtException) {
            throw UnauthorizedException()
        } catch (e: IllegalArgumentException) {
            throw UnauthorizedException()
        }
    }
}
