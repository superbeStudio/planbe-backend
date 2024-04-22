package account.superbe.common.client

import account.superbe.security.refresh_token.Token
import account.superbe.security.refresh_token.TokenRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class TokenDbClient(private val tokenRepo: TokenRepository) : TokenClient {
    override fun setValues(token: String, email: String) {
        tokenRepo.save(Token(refreshToken = token, email = email))
    }

    override fun getValues(token: String): String {
        val refreshInfo = tokenRepo.findByIdOrNull(token) ?: throw IllegalArgumentException("없는 refresh token을 입력하셨습니다.")
        return refreshInfo.email
    }

    override fun delValues(token: String) {
        TODO("Not yet implemented")
    }
}