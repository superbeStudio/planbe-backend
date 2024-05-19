package account.superbe.ui.post

import account.superbe.domain.model.Gen
import java.time.LocalDate

class UserPostRequest (
        val email: String,
        val password: String,
        val nickname: String,
        val sex: Gen,
        val birth: LocalDate
) {
    class Login(
            val email: String,
            val password: String) {

    }
}