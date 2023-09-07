package nexters.linkllet.common.support

import javax.servlet.http.HttpServletRequest

class DeviceHeaderExtractor {

    companion object {
        private const val DEVICE_ID_HEADER = "Device-Id"
        private const val EMPTY_TOKEN = ""

        fun extractDeviceId(request: HttpServletRequest): String {
            return request.getHeader(DEVICE_ID_HEADER)
                ?: return EMPTY_TOKEN
        }
    }
}
