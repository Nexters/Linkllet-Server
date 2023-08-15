package nexters.linkllet.common.support

import javax.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LoginUserResolver(
        private val jwtProvider: JwtProvider,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUserEmail::class.java)
    }

    override fun resolveArgument(
            parameter: MethodParameter,
            mavContainer: ModelAndViewContainer?,
            webRequest: NativeWebRequest,
            binderFactory: WebDataBinderFactory?,
    ): String {
        val request = webRequest.nativeRequest as HttpServletRequest
        val token = AuthorizationExtractor.extractToken(request)
        return jwtProvider.getPayload(token)
    }
}
