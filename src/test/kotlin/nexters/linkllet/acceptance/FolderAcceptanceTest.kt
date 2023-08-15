package nexters.linkllet.acceptance

import nexters.linkllet.acceptance.ArticleStep.Companion.아티클_생성_요청
import nexters.linkllet.acceptance.CommonStep.Companion.응답_확인
import nexters.linkllet.acceptance.FolderStep.Companion.폴더_삭제_요청
import nexters.linkllet.acceptance.FolderStep.Companion.폴더_생성_요청
import nexters.linkllet.acceptance.FolderStep.Companion.폴더_조회_요청
import nexters.linkllet.acceptance.FolderStep.Companion.폴더_조회_응답_확인
import nexters.linkllet.acceptance.FolderStep.Companion.폴더명_변경_요청
import nexters.linkllet.acceptance.MemberStep.Companion.로그인
import nexters.linkllet.acceptance.MemberStep.Companion.회원_가입_요청
import nexters.linkllet.folder.dto.FolderCreateRequest
import nexters.linkllet.folder.dto.FolderUpdateRequest
import nexters.linkllet.member.dto.LoginRequest
import nexters.linkllet.member.dto.MemberSignUpRequest
import nexters.linkllet.util.AcceptanceTest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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
        val token = MemberStep.로그인(LoginRequest("kth990303@naver.com"))
        val 폴더_생성_정보 = FolderCreateRequest("folder")

        // when
        val 폴더_생성_요청_응답 = 폴더_생성_요청(token, 폴더_생성_정보)

        // then
        응답_확인(폴더_생성_요청_응답, HttpStatus.OK)
    }

    /**
     * given: 회원 가입된 사용자가 있다.
     * when: 폴더 이름을 검증 조건에 맞지 않게 지정한후 생성한다
     * then: 폴더 생성에 실패한다
     */
    @Test
    fun `폴더명 10자 초과 생성`() {
        // given
        val token = MemberStep.로그인(LoginRequest("kth990303@naver.com"))
        val 폴더_생성_정보 = FolderCreateRequest("folder_name")

        // when
        val 폴더_생성_요청_응답 = 폴더_생성_요청(token, 폴더_생성_정보)

        // then
        응답_확인(폴더_생성_요청_응답, HttpStatus.BAD_REQUEST)
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
        val token = MemberStep.로그인(LoginRequest("kth990303@naver.com"))
        폴더_생성_요청(token, FolderCreateRequest("folder"))

        // when
        val 폴더_생성_요청_응답 = 폴더_생성_요청(token, FolderCreateRequest("folder_name"))

        // then
        응답_확인(폴더_생성_요청_응답, HttpStatus.BAD_REQUEST)
    }

    /**
     * given: 회원 가입된 사용자가 있다.
     * And: 폴더가 하나가 이상 저장되어 있다
     * And: 폴더에 아티클이 저장되어 있다
     * when: 해당 폴더들을 조회 요청시
     * then: 정상적으로 폴더 목록이 조회된다
     */
    @Test
    fun `폴더 목록 조회`() {
        // given
        val email = "shine@naver.com"
        회원_가입_요청(MemberSignUpRequest(email))
        val token = 로그인(LoginRequest(email))

        val 폴더_조회_응답 = 폴더_조회_요청(token)
        val folderIdOne = 폴더_조회_응답.jsonPath().getLong("folderList[0].id")
        val folderIdTwo = 폴더_조회_응답.jsonPath().getLong("folderList[1].id")
        아티클_생성_요청(token, folderIdOne, "article_1")
        아티클_생성_요청(token, folderIdOne, "article_2")
        아티클_생성_요청(token, folderIdTwo, "article_1")
        아티클_생성_요청(token, folderIdTwo, "article_2")

        // when
        val 폴더_조회_요청_응답 = 폴더_조회_요청(token)

        // then
        폴더_조회_응답_확인(폴더_조회_요청_응답, HttpStatus.OK, 3) // 기본폴더 1개 추가해서 총 4개여야 한다
    }

    /**
     * given: 회원 가입된 사용자가 있다.
     * And: 폴더가 하나 저장되어 있다
     * when: 해당 폴더명을 변경 요청 시
     * then: 정상적으로 폴더명이 변경된다
     */
    @Test
    fun `폴더명 변경`() {
        // given
        val token = 로그인(LoginRequest("kth990303@naver.com"))
        폴더_생성_요청(token, FolderCreateRequest("folder"))

        val folder_id = 폴더_조회_요청(token).jsonPath().getLong("folderList[0].id")

        // when
        val 폴더명_변경_요청_응답 = 폴더명_변경_요청(token, folder_id, FolderUpdateRequest("new_folder"))

        // then
        응답_확인(폴더명_변경_요청_응답, HttpStatus.NO_CONTENT)
    }

    /**
     * given: 회원 가입된 사용자가 있다.
     * And: 폴더 하나가 저장되어 있다
     * And: 폴더를 조회하여 생성된 폴더 id를 조회한다
     * when: 조회된 id에 해당하는 폴더를 삭제 요청시
     * then: 정상적으로 폴더를 삭제한다
     */
    @Test
    fun `폴더 삭제`() {
        // given
        val token = 로그인(LoginRequest("kth990303@naver.com"))
        폴더_생성_요청(token, FolderCreateRequest("folder"))

        val folder_id = 폴더_조회_요청(token).jsonPath().getLong("folderList[0].id")

        // when
        val 폴더_삭제_응답 = 폴더_삭제_요청(token, folder_id)
        응답_확인(폴더_삭제_응답, HttpStatus.NO_CONTENT)

        // then
        폴더_조회_응답_확인(폴더_조회_요청(token), HttpStatus.OK, 1)
    }
}
