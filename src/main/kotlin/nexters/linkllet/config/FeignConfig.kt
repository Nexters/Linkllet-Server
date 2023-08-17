package nexters.linkllet.config

import nexters.linkllet.LinklletApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackageClasses = [LinklletApplication::class])
class FeignConfig {
}
