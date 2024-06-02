package account.superbe.ui.post

import account.superbe.domain.model.Gen
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

class UserPostRequest(
        @Schema(description = "이메일")
        val email: String,
        @Schema(description = "비밀번호")
        val password: String,
        @Schema(description = "닉네임")
        val nickname: String,
        @Schema(description = "성별 - F[여], M[남]")
        val sex: Gen,
        @Schema(description = "생년월일")
        val birth: LocalDate
) {
    class Login(
            val email: String,
            val password: String) {

    }
}