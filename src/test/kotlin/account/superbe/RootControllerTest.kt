package account.superbe

import account.superbe.ui.RootController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(RootController::class)
internal class RootControllerTest(
        @Autowired restDocs: RestDocumentationResultHandler,
        @Autowired mockMvc: MockMvc)
    : AbstractRestDocsTests(restDocs, mockMvc) {
    @Test
    @Throws(Exception::class)
    fun rootrestdocstest() {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print()) // Console 창에서 출력 내용 확인
                .andDo(document("root"))

    }
}