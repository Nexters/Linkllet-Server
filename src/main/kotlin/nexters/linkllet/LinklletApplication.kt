package nexters.linkllet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class LinklletApplication

fun main(args: Array<String>) {
    runApplication<LinklletApplication>(*args)
}
