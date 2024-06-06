package account.superbe.domain.service.user

import account.superbe.application.dto.UserDto
import account.superbe.infra.UserFactory
import account.superbe.infra.UserJpaRepository
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserService(private val userRepo: UserJpaRepository, private val userFactory: UserFactory) {
    val log: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun getUserByEmailNonNull(email: String): UserDto {
        val user = userRepo.findByEmail(email).orElseThrow { throw IllegalArgumentException("로그인 정보를 다시 확인해주세요") }
        log.info("[getUserByEmailNonNull] email = {}", email)
        return UserDto(email = user.email, password = user.password, nickname = user.nickname, sex = user.sex,
                birth = user.birth, userSeq = user.userSequence)
    }
}