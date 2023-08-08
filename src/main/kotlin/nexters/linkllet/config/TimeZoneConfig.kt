package nexters.linkllet.config

import org.springframework.context.annotation.Configuration
import java.util.*
import javax.annotation.PostConstruct

@Configuration
class TimeZoneConfig {

    @PostConstruct
    fun setSeoulTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE_REGION))
    }

    companion object {
        const val TIME_ZONE_REGION = "Asia/Seoul"
    }
}
