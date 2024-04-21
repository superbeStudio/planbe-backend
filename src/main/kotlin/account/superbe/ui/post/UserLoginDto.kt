package account.superbe.ui.post

import account.superbe.security.TokenDto

class UserLoginDto(
        val token: TokenDto,
        val email: String,
) {
}