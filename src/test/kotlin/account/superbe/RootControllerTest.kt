package account.superbe

import account.superbe.application.UserApplicationService
import account.superbe.infra.GoalJpaRepository
import account.superbe.infra.UserJpaRepository
import account.superbe.security.refresh_token.TokenRepository
import account.superbe.ui.RootController
import account.superbe.ui.UserController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(RootController::class)
@AutoConfigureMockMvc
internal class RootControllerTest(
        @Autowired restDocs: RestDocumentationResultHandler,
        @Autowired mockMvc: MockMvc)
    : AbstractRestDocsTests(restDocs, mockMvc) {
    @SpyBean
    private lateinit var userController: UserController
    @MockBean
    private lateinit var userService: UserApplicationService
    @MockBean
    private lateinit var userJpaRepository: UserJpaRepository
    @MockBean
    private lateinit var tokenRepository: TokenRepository
    @MockBean
    private lateinit var goalJpaRepository: GoalJpaRepository
    
    @Test
    @Throws(Exception::class)
    fun rootRestDocsTest() {
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print()) // Console 창에서 출력 내용 확인
                .andDo(document("root"))

    }
}