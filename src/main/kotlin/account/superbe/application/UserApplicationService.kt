package account.superbe.application

import account.superbe.application.dto.UserDto
import account.superbe.domain.service.UserService
import account.superbe.infra.UserFactory
import account.superbe.infra.UserJpaRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

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
        return user.uuid!!
    }

    fun getUserInfo(userId: Long) : UserDto{
        val user = userRepo.findByIdOrNull(userId) ?: throw IllegalArgumentException("잘못된 유저 아이디를 입력하셨습니다.")
        return UserDto(uuid = user.uuid,
            email = user.email,
            nickname = user.nickname,
            sex = user.sex,
            age = user.age,
            currencyMain = user.currencyMain,
            modeScreen = user.modeScreen,
            createDate = user.createDate,
            updateDate= user.updateDate)
    }
}