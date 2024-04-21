package account.superbe.application

import account.superbe.application.dto.UserDto
import account.superbe.domain.service.UserService
import account.superbe.infra.UserFactory
import account.superbe.infra.UserJpaRepository
import account.superbe.security.JwtTokenProvider
import account.superbe.security.TokenDto
import account.superbe.ui.post.UserLoginDto
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class UserApplicationService (
        private val userService: UserService,
        private val userFactory: UserFactory,
        private val userRepo: UserJpaRepository,
        private val passwordEncoder: PasswordEncoder,
        private val authBuilder: AuthenticationManagerBuilder,
        private val tokenProvider: JwtTokenProvider
){
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

    fun login(data: UserDto.Login): UserLoginDto {
        val user = userRepo.findByEmail(data.email).orElseThrow {throw IllegalArgumentException("로그인 정보를 다시 확인해주세요")}
        if (!passwordEncoder.matches(data.password, user.password)) {
            throw IllegalArgumentException("로그인 정보를 다시 확인해주세요")
        }
        val authenticationToken =
                UsernamePasswordAuthenticationToken(user.email, data.password)

        val authentication: Authentication =
                authBuilder.getObject().authenticate(authenticationToken)

        val generateToken = tokenProvider.generateToken(authentication)
        return UserLoginDto(token = generateToken, email = user.email)
    }
}