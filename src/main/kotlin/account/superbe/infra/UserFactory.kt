package account.superbe.infra

import account.superbe.application.dto.UserDto
import account.superbe.domain.model.Role
import account.superbe.domain.model.User
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserFactory(private val passwordEncoder: PasswordEncoder) {
    fun create(user: UserDto): User{
        val encodePassword = passwordEncoder.encode(user.password);
        return User(email = user.email, password = encodePassword, age = user.age, sex = user.sex, nickname = user.nickname, role = Role.ROLE_N)
    }
}