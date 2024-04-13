package account.superbe.application

import account.superbe.application.dto.UserDto
import account.superbe.domain.service.UserService
import account.superbe.infra.UserFactory
import account.superbe.infra.UserJpaRepository
import account.superbe.ui.post.UserPostRequest
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@RequiredArgsConstructor
class UserApplicationService (private val userService: UserService, private val userFactory: UserFactory, private val userRepo: UserJpaRepository){
    @Transactional
    fun createUser(user: UserDto) : Long{
        val user = userFactory.create(user)
        if(userService.existsEmail(user)) {
            throw IllegalArgumentException("이미 등록된 이메일입니다.")
        }
        userRepo.save(user)
        return user.uuid!!;
    }
}