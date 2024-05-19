package account.superbe.domain.service

import account.superbe.application.dto.UserDto
import account.superbe.infra.UserFactory
import account.superbe.infra.UserJpaRepository
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class UserService(private val userRepo: UserJpaRepository, private val userFactory: UserFactory) {
    val log: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun getUserByEmailNonNull(email: String): UserDto {
        val user = userRepo.findByEmail(email).orElseThrow { throw IllegalArgumentException("로그인 정보를 다시 확인해주세요") }
        log.info("[getUserByEmailNonNull] email = {}", email)
        return UserDto(email = user.email, nickname = user.nickname, sex = user.sex, birth = user.birth,
                userSeq = user.userSequence)
    }

    @Transactional
    fun createUser(data: UserDto) : Long{
        val user = userFactory.create(data)
        if(userRepo.existsByEmail(user.email)) {
            log.info("[createUser] 회원가입 실패, 중복 email = {}", data.email)
            throw IllegalArgumentException("이미 등록된 이메일입니다.")
        }
        userRepo.save(user)
        log.info("[createUser] 회원가입 성공 PK = {}", user.userSequence)
        return user.userSequence!!
    }


}