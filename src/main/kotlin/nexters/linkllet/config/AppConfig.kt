package nexters.linkllet.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan
class AppConfig {

    @Bean
    fun jwtConfigProperties(): JwtConfigProperties {
        return JwtConfigProperties()
    }
}
