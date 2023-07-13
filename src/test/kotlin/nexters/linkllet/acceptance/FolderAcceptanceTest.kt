package nexters.linkllet.acceptance

import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nexters.linkllet.acceptance.FolderStep.Companion.응답_확인
import nexters.linkllet.acceptance.FolderStep.Companion.폴더_생성_요청
import nexters.linkllet.acceptance.FolderStep.Companion.폴더_조회_요청
import nexters.linkllet.acceptance.FolderStep.Companion.폴더_조회_응답_확인
import nexters.linkllet.folder.dto.FolderCreateRequest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus

@DisplayName("인수 : Folder")
class FolderAcceptanceTest : AcceptanceTest() {

    /**
     * given: 회원 가입된 사용자가 있다.
     * when: 폴더 이름을 지정한후 생성한다
     * then: 성공적으로 폴더를 생성한다
     */
    @Test
    fun `폴더 생성`() {
        // given
        val 폴더_생성_정보 = FolderCreateRequest("folder_name")

        // when
        val 폴더_생성_요청_응답 = 폴더_생성_요청("device_id", 폴더_생성_정보)

        // then
        응답_확인(폴더_생성_요청_응답, HttpStatus.OK)
    }

    /**
     * given: 회원 가입된 사용자가 있다.
     * And: 폴더 하나가 저장되어 있다
     * when: 폴더 이름을 이미 지정된 폴더 이름과 동일하게 지정한후 생성한다
     * then: 폴더를 생성할 수 없다
     */
    @Test
    fun `폴더 중복 이름 생성`() {
        // given
        폴더_생성_요청("device_id", FolderCreateRequest("folder_name"))

        // when
        val 폴더_생성_요청_응답 = 폴더_생성_요청("device_id", FolderCreateRequest("folder_name"))

        // then
        응답_확인(폴더_생성_요청_응답, HttpStatus.BAD_REQUEST)
    }

    /**
     * given: 회원 가입된 사용자가 있다.
     * And: 폴더가 하나가 이상 저장되어 있다
     * when: 해당 폴더들을 조회 요청시
     * then: 정상적으로 폴더 목록이 조회된다
     */
    @Test
    fun `폴더 목록 조회`() {
        // given
        폴더_생성_요청("device_id", FolderCreateRequest("folder_name_1"))
        폴더_생성_요청("device_id", FolderCreateRequest("folder_name_2"))
        폴더_생성_요청("device_id", FolderCreateRequest("folder_name_3"))

        // when
        val 폴더_조회_요청_응답 = 폴더_조회_요청("device_id")

        // then
        폴더_조회_응답_확인(폴더_조회_요청_응답, HttpStatus.OK, 3)
    }
}
