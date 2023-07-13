package nexters.linkllet.acceptance

import nexters.linkllet.acceptance.ArticleStep.Companion.아티클_생성_요청
import nexters.linkllet.acceptance.ArticleStep.Companion.아티클_조회_응답_확인
import nexters.linkllet.acceptance.ArticleStep.Companion.폴더의_모든_아티클_조회_요청
import nexters.linkllet.acceptance.CommonStep.Companion.응답_확인
import nexters.linkllet.acceptance.FolderStep.Companion.폴더_생성_요청
import nexters.linkllet.acceptance.FolderStep.Companion.폴더_조회_요청
import nexters.linkllet.folder.dto.FolderCreateRequest
import nexters.linkllet.util.AcceptanceTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("인수 : Article")
class ArticleAcceptanceTest : AcceptanceTest() {

    /**
     * given: 회원 가입된 사용자가 있다.
     * And: 폴더 하나가 저장되어 있다
     * And: 폴더를 조회하여 생성된 폴더 id를 조회한다
     * when: 조회된 id에 해당하는 폴더에 링크 추가를 요청시
     * then: 정상적으로 폴더에 링크가 추가된다
     */
    @Test
    fun `사용자 폴더에 링크 저장하기`() {
        // given
        폴더_생성_요청("device_id", FolderCreateRequest("folder_name"))
        val folderId = 폴더_조회_요청("device_id").jsonPath().getLong("folderList[0].id")

        // when
        val 아티클_생성_응답 = 아티클_생성_요청("device_id", folderId, "article_name")

        // then
        응답_확인(아티클_생성_응답, HttpStatus.OK)
    }

    /**
     * given: 회원 가입된 사용자가 있다.
     * And: 폴더 하나가 저장되어 있다
     * And: 폴더 내부에 링크가 5개 저장되어 있다
     * when: 조회된 id에 해당하는 폴더를 클릭할 시
     * then: 폴더 내부의 모든 링크가 보여진다
     */
    @Test
    fun `사용자 폴더에 모든 링크 조회하기`() {
        // given
        폴더_생성_요청("device_id", FolderCreateRequest("folder_name"))
        val folderId = 폴더_조회_요청("device_id").jsonPath().getLong("folderList[0].id")

        아티클_생성_요청("device_id", folderId, "article_name_1")
        아티클_생성_요청("device_id", folderId, "article_name_2")
        아티클_생성_요청("device_id", folderId, "article_name_3")
        아티클_생성_요청("device_id", folderId, "article_name_4")
        아티클_생성_요청("device_id", folderId, "article_name_5")

        // when
        val 폴더_아티클_조회_응답클 = 폴더의_모든_아티클_조회_요청(folderId)

        // then
        아티클_조회_응답_확인(폴더_아티클_조회_응답클, HttpStatus.OK, 5)
    }
}
