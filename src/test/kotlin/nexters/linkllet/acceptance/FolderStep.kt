package nexters.linkllet.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nexters.linkllet.folder.dto.FolderCreateRequest
import nexters.linkllet.folder.dto.FolderUpdateRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class FolderStep {

    companion object {

        fun 폴더_생성_요청(token: String, folderCreateRequest: FolderCreateRequest): ExtractableResponse<Response> =
                RestAssured
                        .given().log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .auth().oauth2(token)
                        .body(folderCreateRequest)
                        .`when`().post("/api/v1/folders")
                        .then().log().all()
                        .extract()

        fun 폴더_조회_요청(token: String): ExtractableResponse<Response> =
                RestAssured
                        .given().log().all()
                        .auth().oauth2(token)
                        .`when`().get("/api/v1/folders")
                        .then().log().all()
                        .extract()

        fun 폴더명_변경_요청(token: String, folder_id: Long, folderUpdateRequest: FolderUpdateRequest): ExtractableResponse<Response> =
                RestAssured
                        .given().log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .auth().oauth2(token)
                        .body(folderUpdateRequest)
                        .`when`().put("/api/v1/folders/{id}", folder_id)
                        .then().log().all()
                        .extract()

        fun 폴더_삭제_요청(token: String, folder_id: Long) =
                RestAssured
                        .given().log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .auth().oauth2(token)
                        .`when`().delete("/api/v1/folders/{id}", folder_id)
                        .then().log().all()
                        .extract()

        fun 폴더_조회_응답_확인(폴더_조회_요청_응답: ExtractableResponse<Response>, httpStatus: HttpStatus, count: Int) {
            assertAll(
                    { Assertions.assertThat(폴더_조회_요청_응답.statusCode()).isEqualTo(httpStatus.value()) },
                    { Assertions.assertThat(폴더_조회_요청_응답.jsonPath().getList<Any>("folderList").size).isEqualTo(count) }
            )
        }
    }
}
