package account.superbe.ui.post

import account.superbe.domain.model.Gen

class UserPostRequest (
        val email: String,
        val password: String,
        val nickname: String,
        val sex: Gen,
        val age: Int
) {

}