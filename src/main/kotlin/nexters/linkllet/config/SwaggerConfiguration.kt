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

    @Bean
    fun api(): OpenAPI {
        val info = Info()
                .title("Linkllet 백엔드 API 명세")
                .description("springdoc을 이용한 Swagger API 문서입니다.")
                .version("1.0")
                .contact(Contact().name("springdoc 공식문서").url("https://springdoc.org/"))

        val deviceHeader = SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .`in`(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION)

        return OpenAPI()
                .components(Components().addSecuritySchemes("JWT", deviceHeader))
                .info(info)
    }
}
