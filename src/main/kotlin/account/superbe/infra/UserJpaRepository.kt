package account.superbe.infra

import account.superbe.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserJpaRepository : JpaRepository<User, Long>{
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Optional<User>
}