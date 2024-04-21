package account.superbe.infra

import account.superbe.application.dto.UserDto
import account.superbe.config.SecurityConfig
import account.superbe.domain.model.Role
import account.superbe.domain.model.User
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserFactory(private val securityConfig: SecurityConfig) {
    fun create(user: UserDto): User{
        val encodePassword = securityConfig.passwordEncoder().encode(user.password);
        return User(email = user.email, password = encodePassword, age = user.age, sex = user.sex, nickname = user.nickname, role = Role.N)
    }
}