package nexters.linkllet.common.support

import nexters.linkllet.common.exception.dto.UnauthorizedException
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Component
class LoginUserResolver(
        private val jwtProvider: JwtProvider,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    override fun resolveArgument(
            parameter: MethodParameter,
            mavContainer: ModelAndViewContainer?,
            webRequest: NativeWebRequest,
            binderFactory: WebDataBinderFactory?,
    ): String {
        val request = webRequest.nativeRequest as HttpServletRequest

        val deviceId = DeviceHeaderExtractor.extractDeviceId(request)
        val jwtToken = AuthorizationExtractor.extractToken(request)

        if (hasBothDeviceIdAndToken(deviceId, jwtToken)) throw UnauthorizedException()
        else if (isAppleOAuthLogin(deviceId)) return jwtProvider.getPayload(jwtToken)
        else if (isKakaoOAuthLogin(jwtToken)) return deviceId
        else throw UnauthorizedException()
    }

    private fun hasBothDeviceIdAndToken(deviceId: String, jwtToken: String) =
        !deviceId.isNullOrBlank() && !jwtToken.isNullOrBlank()

    private fun isAppleOAuthLogin(deviceId: String) = deviceId.isNullOrBlank()

    private fun isKakaoOAuthLogin(jwtToken: String) = jwtToken.isNullOrBlank()
}
