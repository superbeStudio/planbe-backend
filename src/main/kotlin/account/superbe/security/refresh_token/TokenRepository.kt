package account.superbe.security.refresh_token

import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository: JpaRepository<Token, String> {
}