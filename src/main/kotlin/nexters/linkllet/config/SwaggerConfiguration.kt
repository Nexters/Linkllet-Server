package nexters.linkllet.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class SwaggerConfiguration {

    companion object {
        private const val DEVICE_ID_HEADER = "Device-Id"
    }

    @Bean
    fun api(): OpenAPI {
        val info = Info()
            .title("Linkllet 백엔드 API 명세")
            .description("springdoc을 이용한 Swagger API 문서입니다.")
            .version("1.2.0")
            .contact(Contact().name("springdoc 공식문서").url("https://springdoc.org/"))

        val deviceIdHeader = SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .`in`(SecurityScheme.In.HEADER)
            .name(DEVICE_ID_HEADER)

        val deviceTokenHeader = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION)

        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes("JWT", deviceTokenHeader)
                    .addSecuritySchemes(DEVICE_ID_HEADER, deviceIdHeader)
            )
                .info(info)
    }
}
