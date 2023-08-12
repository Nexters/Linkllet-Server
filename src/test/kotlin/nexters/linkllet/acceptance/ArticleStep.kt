package nexters.linkllet.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nexters.linkllet.folder.dto.ArticleCreateRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class ArticleStep {

    companion object {
        fun 아티클_생성_요청(token: String, folderId: Long, articleName: String): ExtractableResponse<Response> =
                RestAssured
                        .given().log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .auth().oauth2(token)
                        .body(ArticleCreateRequest(articleName, "https://blogshine.tistory.com/1"))
                        .`when`().post("/api/v1/folders/{id}/articles", folderId)
                        .then().log().all()
                        .extract()

        fun 폴더의_모든_아티클_조회_요청(token: String, folderId: Long): ExtractableResponse<Response> =
                RestAssured
                        .given().log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .auth().oauth2(token)
                        .`when`().get("/api/v1/folders/{id}/articles", folderId)
                        .then().log().all()
                        .extract()

        fun 아티클_삭제_요청(token: String, folderId: Long, firstArticleId: Long) =
                RestAssured
                        .given().log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .auth().oauth2(token)
                        .pathParam("id", folderId)
                        .pathParam("articleId", firstArticleId)
                        .`when`().delete("/api/v1/folders/{id}/articles/{articleId}")
                        .then().log().all()
                        .extract()

        fun 아티클_조회_응답_확인(response: ExtractableResponse<Response>, httpStatus: HttpStatus, count: Int) {
            assertAll(
                    { Assertions.assertThat(response.statusCode()).isEqualTo(httpStatus.value()) },
                    { Assertions.assertThat(response.jsonPath().getList<Any>("articleList").size).isEqualTo(count) }
            )
        }
    }
}
