package nexters.linkllet.common.support.apple

import nexters.linkllet.common.exception.dto.BadRequestException

data class ApplePublicKeys(
        val keys: List<ApplePublicKey>,
) {

    fun getMatchesKey(alg: String?, kid: String?): ApplePublicKey {
        return keys
                .stream()
                .filter { (k, a): ApplePublicKey -> a == alg && k == kid }
                .findFirst()
                .orElseThrow { BadRequestException("Apple JWT 값의 alg, kid 정보가 올바르지 않습니다.") }
    }
}

data class ApplePublicKey(
        val kty: String,
        val kid: String,
        val use: String,
        val alg: String,
        val n: String,
        val e: String,
)
