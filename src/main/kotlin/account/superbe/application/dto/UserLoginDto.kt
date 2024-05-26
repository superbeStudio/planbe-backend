package account.superbe.application.dto

import account.superbe.security.TokenDto

class UserLoginDto(
        val token: TokenDto,
        val email: String,
) {
}