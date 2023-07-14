package nexters.linkllet.acceptance

import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions
import org.springframework.http.HttpStatus

class CommonStep {

    companion object {

        fun 응답_확인(폴더_생성_요청_응답: ExtractableResponse<Response>, httpStatus: HttpStatus) =
            Assertions.assertThat(폴더_생성_요청_응답.statusCode()).isEqualTo(httpStatus.value())
    }
}
