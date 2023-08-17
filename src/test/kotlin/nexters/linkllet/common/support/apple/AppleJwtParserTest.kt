package nexters.linkllet.common.support.apple

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.security.KeyPairGenerator
import java.util.*
import nexters.linkllet.common.exception.dto.UnauthorizedException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AppleJwtParserTest {

    private val appleJwtParser = AppleJwtParser()

    @Test
    fun `Apple identity token으로 헤더를 파싱한다`() {
        // given
        val now = Date()
        val keyPair = KeyPairGenerator.getInstance("RSA").genKeyPair()
        val privateKey = keyPair.private

        val idToken = Jwts.builder()
            .setHeaderParam("kid", "W2R4HXF3K")
            .claim("id", "12345678")
            .setIssuer("iss")
            .setIssuedAt(now)
            .setAudience("aud")
            .setExpiration(Date(now.time + 1000 * 60 * 60 * 24))
            .signWith(privateKey, SignatureAlgorithm.RS256)
            .compact()

        // when
        val actual = appleJwtParser.parseHeaders(idToken)

        // then
        assertThat(actual).containsKeys("alg", "kid")
    }

    @Test
    fun `올바르지 않은 형식의 Apple identity token으로 헤더를 파싱하면 예외를 반환한다`() {
        assertThrows<UnauthorizedException> { appleJwtParser.parseHeaders("invalidToken") }
    }

    @Test
    fun `Apple identity token, PublicKey를 받아 사용자 정보가 포함된 Claims를 반환한다`() {
        // given
        val expected = "11111111"
        val now = Date()
        val keyPair = KeyPairGenerator.getInstance("RSA").genKeyPair()
        val publicKey = keyPair.public
        val privateKey = keyPair.private

        val idToken = Jwts.builder()
            .setHeaderParam("kid", "W2R4HXF3K")
            .claim("id", "12345678")
            .setIssuer("iss")
            .setIssuedAt(now)
            .setAudience("aud")
            .setSubject(expected)
            .setExpiration(Date(now.time + 1000 * 60 * 60 * 24))
            .signWith(privateKey, SignatureAlgorithm.RS256)
            .compact()

        // when
        val claims = appleJwtParser.parsePublicKeyAndGetClaims(idToken, publicKey)

        // then
        assertThat(claims.subject).isEqualTo(expected)
    }

    @Test
    fun `만료된 Apple identity token을 받으면 Claims 획득 시에 예외를 반환한다`() {
        // given
        val now = Date()
        val keyPair = KeyPairGenerator.getInstance("RSA").genKeyPair()
        val publicKey = keyPair.public
        val privateKey = keyPair.private

        val expiredIdToken = Jwts.builder()
            .setHeaderParam("kid", "W2R4HXF3K")
            .claim("id", "12345678")
            .setIssuer("iss")
            .setIssuedAt(now)
            .setAudience("aud")
            .setSubject("11111111")
            .setExpiration(Date(now.time - 1))
            .signWith(privateKey, SignatureAlgorithm.RS256)
            .compact()

        // when
        // then
        assertThrows<UnauthorizedException> { appleJwtParser.parsePublicKeyAndGetClaims(expiredIdToken, publicKey) }
    }

    @Test
    fun `올바르지 않은 public Key로 Claims 획득 시에 예외를 반환한다`() {
        // given
        val now = Date()
        val keyPair = KeyPairGenerator.getInstance("RSA").genKeyPair()
        val privateKey = keyPair.private
        val diffPublicKey = KeyPairGenerator.getInstance("RSA").genKeyPair().public

        val expiredIdToken = Jwts.builder()
            .setHeaderParam("kid", "W2R4HXF3K")
            .claim("id", "12345678")
            .setIssuer("iss")
            .setIssuedAt(now)
            .setAudience("aud")
            .setSubject("11111111")
            .setExpiration(Date(now.time - 1))
            .signWith(privateKey, SignatureAlgorithm.RS256)
            .compact()

        // when
        // then
        assertThrows<UnauthorizedException> { appleJwtParser.parsePublicKeyAndGetClaims(expiredIdToken, diffPublicKey) }
    }
}
