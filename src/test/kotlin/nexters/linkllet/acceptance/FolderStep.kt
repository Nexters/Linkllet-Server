package nexters.linkllet.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nexters.linkllet.folder.dto.FolderCreateRequest
import org.assertj.core.api.Assertions
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class FolderStep {

    companion object {

        fun 폴더_생성_요청(deviceId: String, folderCreateRequest: FolderCreateRequest): ExtractableResponse<Response> =
            RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Device-Id", deviceId)
                .body(folderCreateRequest)
                .`when`().post("/api/v1/folders")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()

        fun 응답_확인(폴더_생성_요청_응답: ExtractableResponse<Response>, httpStatus: HttpStatus) =
            Assertions.assertThat(폴더_생성_요청_응답.statusCode()).isEqualTo(httpStatus.value())
    }
}
