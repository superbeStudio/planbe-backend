package account.superbe.infra.user

import account.superbe.domain.model.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long>{
}