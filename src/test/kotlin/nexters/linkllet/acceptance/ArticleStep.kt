package nexters.linkllet.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nexters.linkllet.folder.dto.ArticleCreateRequest
import org.springframework.http.MediaType

class ArticleStep {

    companion object {
        fun 아티클_생성_요청(deviceId: String, folderId: Long): ExtractableResponse<Response> =
            RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Device-Id", deviceId)
                .body(ArticleCreateRequest("new_article", "https://blogshine.tistory.com/1"))
                .`when`().post("/api/v1/folders/{id}/articles", folderId)
                .then().log().all()
                .extract()
    }
}
