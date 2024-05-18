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
import org.slf4j.Logger
import org.slf4j.LoggerFactory


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
    val log: Logger = LoggerFactory.getLogger(UserApplicationService::class.java)

    @Transactional
    fun createUser(data: UserDto) : Long{
        val user = userFactory.create(data)
        if(userService.existsEmail(user)) {
            log.info("[createUser] 회원가입 실패, 중복 email = {}", data.email)
            throw IllegalArgumentException("이미 등록된 이메일입니다.")
        }
        userRepo.save(user)
        log.info("[createUser] 회원가입 성공 PK = {}", user.seq)
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
            log.info("[login] 로그인 실패 = {}", data.email)
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
            log.info("[getAccessToken] 만료된 refresh 토큰 = {}", refreshToken)
            throw IllegalArgumentException("만료된 토큰")
        }
        val user = userRepo.findByEmail(email)
                .orElseThrow {
                    log.info("[getAccessToken] refresh 토큰의 유저 정보가 존재하지않음 = {}", email)
                    throw IllegalArgumentException("refresh 토큰의 유저 정보가 존재하지않습니다.")
                }

        val generateToken = tokenProvider.getAccessToken(user.email, user.password, null)
        tokenClient.setValues(generateToken.refreshToken, user.email)
        return TokenDto(refreshToken = refreshToken, accessToken = generateToken.accessToken)
    }
}