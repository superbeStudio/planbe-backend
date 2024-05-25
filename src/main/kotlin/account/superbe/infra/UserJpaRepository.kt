package account.superbe.infra

import account.superbe.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Meta
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserJpaRepository : JpaRepository<User, Long>{
    @Meta(comment = "email duplicated check")
    fun existsByEmail(email: String): Boolean
    @Meta(comment = "find user by email")
    fun findByEmail(email: String): Optional<User>

}