package account.superbe

import account.superbe.application.UserApplicationService
import account.superbe.application.dto.UserDto
import account.superbe.common.io.ResponseDto
import account.superbe.domain.model.Gen
import account.superbe.infra.UserJpaRepository
import account.superbe.security.refresh_token.TokenRepository
import account.superbe.ui.UserController
import account.superbe.ui.post.UserPostRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers.any
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.*


@WebMvcTest(UserController::class)
class UserControllerTest (
        @Autowired restDocs: RestDocumentationResultHandler,
        @Autowired mockMvc: MockMvc)
    : AbstractRestDocsTests(restDocs, mockMvc) {

    companion object {
        private const val END_POINT = "/api/user"
    }
    @SpyBean private lateinit var userController: UserController
    @MockBean private lateinit var userService: UserApplicationService
    @MockBean private lateinit var userJpaRepository: UserJpaRepository
    @MockBean private lateinit var tokenRepository: TokenRepository

    private val objectMapper: ObjectMapper = ObjectMapper()
    private val user: UserPostRequest = UserPostRequest (email = "test@email.com", password = "test123", nickname= "test", sex = Gen.F, age= 32)

    @Test
    fun userCreateTest() {

        // when
        val result = mockMvc.perform(RestDocumentationRequestBuilders.post(END_POINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )

        // then
        result.andExpect(status().isOk)
                .andDo(document("user",
                requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호"),
                        fieldWithPath("nickname").description("닉네임"),
                        fieldWithPath("sex").description("성별").type(JsonFieldType.STRING), // 성별은 문자열로 전송됨
                        fieldWithPath("age").description("나이").type(JsonFieldType.NUMBER) // 나이는 숫자로 전송됨
                ),
                responseFields(
                        fieldWithPath("time").type(JsonFieldType.STRING).description("응답 시간"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지").optional(),
                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("사용자 PK")
                )
        ))
    }

    @Test
    fun getUser() {
        val userId = 1L;
        val userDto = UserDto(
                seq = userId,
                email = "test@example.com",
                nickname = "test_user",
                sex = Gen.M,
                age = 30,
                createDate = LocalDateTime.now(),
                updateDate = LocalDateTime.now()
        )
        val response = ResponseDto(data = userDto)

        `when`(userController.getUserInfo(userId)).thenReturn(response)

        val result = mockMvc.perform(
                RestDocumentationRequestBuilders.get("$END_POINT/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
        )

        result.andExpect(status().isOk)
                .andDo(document("user-get",
                        pathParameters(
                                parameterWithName("userId").description("유저 PK")
                        ),
                        responseFields(
                                fieldWithPath("time").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과 메시지").optional(),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터").optional(),
                                fieldWithPath("data.seq").type(JsonFieldType.NUMBER).description("사용자 PK"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data.password").type(JsonFieldType.STRING).description("비밀번호").optional(),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("data.sex").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.currencyMain").type(JsonFieldType.NUMBER).description("").optional(),
                                fieldWithPath("data.modeScreen").type(JsonFieldType.NUMBER).description("").optional(),
                                fieldWithPath("data.createDate").type(JsonFieldType.STRING).description("생성일"),
                                fieldWithPath("data.updateDate").type(JsonFieldType.STRING).description("수정일")
                        )
                ))
    }

}