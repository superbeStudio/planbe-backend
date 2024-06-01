package account.superbe.infra

import account.superbe.application.dto.UserDto
import account.superbe.domain.model.Role
import account.superbe.domain.model.User
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserFactory(private val passwordEncoder: PasswordEncoder, private val userRepo: UserJpaRepository) {
    val log: Logger = LoggerFactory.getLogger(UserFactory::class.java)
    fun create(user: UserDto): User{
        if(userRepo.existsByEmail(user.email)) {
            log.info("[createUser] 회원가입 실패, 중복 email = {}", user.email)
            throw IllegalArgumentException("이미 등록된 이메일입니다.")
        }
        val encodePassword = passwordEncoder.encode(user.password);
        return User(email = user.email, password = encodePassword, birth = user.birth, sex = user.sex, nickname = user.nickname, role = Role.ROLE_N)
    }
}