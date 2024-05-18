package account.superbe.application

import account.superbe.application.dto.UserDto
import account.superbe.common.client.TokenClient
import account.superbe.domain.service.UserService
import account.superbe.infra.UserFactory
import account.superbe.infra.UserJpaRepository
import account.superbe.security.JwtTokenProvider
import account.superbe.security.TokenDto
import account.superbe.ui.post.UserLoginDto
import lombok.RequiredArgsConstructor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@RequiredArgsConstructor
class UserApplicationService (
        private val userService: UserService,
        private val userFactory: UserFactory,
        private val userRepo: UserJpaRepository,
        private val passwordEncoder: PasswordEncoder,
        private val tokenProvider: JwtTokenProvider,
        private val tokenClient: TokenClient
){
    @Transactional
    fun createUser(user: UserDto) : Long{
        val user = userFactory.create(user)
        if(userService.existsEmail(user)) {
            throw IllegalArgumentException("이미 등록된 이메일입니다.")
        }
        userRepo.save(user)
        return user.seq!!
    }

    @Transactional
    fun getUserInfo(userId: Long) : UserDto{
        val user = userRepo.findByIdOrNull(userId) ?: throw IllegalArgumentException("잘못된 유저 아이디를 입력하셨습니다.")
        return UserDto(seq = user.seq,
            email = user.email,
            nickname = user.nickname,
            sex = user.sex,
            age = user.age,
            currencyMain = user.currencyMain,
            modeScreen = user.modeScreen,
            createDate = user.createDate,
            updateDate= user.updateDate)
    }

    @Transactional(readOnly = true)
    fun login(data: UserDto.Login): UserLoginDto {
        val user = userRepo.findByEmail(data.email).orElseThrow {throw IllegalArgumentException("로그인 정보를 다시 확인해주세요")}
        if (!passwordEncoder.matches(data.password, user.password)) {
            throw IllegalArgumentException("로그인 정보를 다시 확인해주세요")
        }
        val generateToken = tokenProvider.getAccessToken(user.email, data.password, user)
        tokenClient.setValues(generateToken.refreshToken, user.email)
        return UserLoginDto(token = generateToken, email = user.email)
    }

    @Transactional(readOnly = true)
    fun getAccessToken(refreshToken: String): TokenDto {
        val email = tokenClient.getValues(refreshToken)
        if(tokenProvider.isRefreshTokenExpired(refreshToken)) {
            // TODO: 만료토큰 관리로직 추가 필요
            throw IllegalArgumentException("만료된 토큰")
        }
        val user = userRepo.findByEmail(email)
                .orElseThrow { throw IllegalArgumentException("없는 이메일정보입니다.") }

        val generateToken = tokenProvider.getAccessToken(user.email, user.password, null)
        tokenClient.setValues(generateToken.refreshToken, user.email)
        return TokenDto(refreshToken = refreshToken, accessToken = generateToken.accessToken)
    }
}