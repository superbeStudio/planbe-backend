package account.superbe

import account.superbe.config.RestDocsConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.context.annotation.Import
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter


@Import(RestDocsConfig::class)
@ExtendWith(RestDocumentationExtension::class)
@AutoConfigureRestDocs
abstract class AbstractRestDocsTests(
        @Autowired var restDocs: RestDocumentationResultHandler,
        @Autowired var mockMvc: MockMvc
) {
    @BeforeEach
    fun setUp(
            context: WebApplicationContext,
            restDocumentation: RestDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation))
                .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
                .alwaysDo<DefaultMockMvcBuilder>(restDocs)
                .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
                .build()
    }
}