package nexters.linkllet.config

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Bean


class KakaoFeignConfiguration {

    @Bean
    fun requestInterceptor(): RequestInterceptor? {
        return RequestInterceptor { template: RequestTemplate ->
            template.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
        }
    }
}
