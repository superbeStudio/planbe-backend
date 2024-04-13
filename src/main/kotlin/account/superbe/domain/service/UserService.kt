package account.superbe.domain.service

import account.superbe.application.dto.UserDto
import account.superbe.config.SecurityConfig
import account.superbe.domain.model.User
import account.superbe.infra.UserJpaRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserService(private val userRepo: UserJpaRepository) {
    fun existsEmail(user: User): Boolean{
        return userRepo.existsByEmail(user.email);
    }


}