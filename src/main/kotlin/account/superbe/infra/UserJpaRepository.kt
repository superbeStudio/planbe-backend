package account.superbe.infra

import account.superbe.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long>{
    fun existsByEmail(email: String): Boolean
}