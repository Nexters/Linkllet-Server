package nexters.linkllet.common.support

import nexters.linkllet.common.exception.dto.UnAuthorizedException
import org.springframework.web.context.request.NativeWebRequest

private const val DEVICE_ID_HEADER = "Device-Id"

class DeviceHeaderExtractor {

    companion object {
        fun extractDeviceId(request: NativeWebRequest): String {
            return request.getHeader(DEVICE_ID_HEADER)
                    ?: throw UnAuthorizedException("디바이스 ID가 존재하지 않습니다.")
        }
    }
}
