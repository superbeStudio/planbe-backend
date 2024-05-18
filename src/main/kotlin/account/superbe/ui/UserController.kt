package account.superbe.ui

import account.superbe.application.UserApplicationService
import account.superbe.application.dto.UserDto
import account.superbe.common.io.ResponseDto
import account.superbe.security.TokenDto
import account.superbe.ui.post.UserLoginDto
import account.superbe.ui.post.UserPostRequest
import lombok.RequiredArgsConstructor
import org.aspectj.apache.bcel.classfile.JavaClass
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
class UserController(private val userService: UserApplicationService) {
    val log: Logger = LoggerFactory.getLogger(UserApplicationService::class.java)

    @PostMapping
    fun createUser(@RequestBody data: UserPostRequest): ResponseDto<Long> {
        log.info("[createUser] 회원가입 email = {}", data.email)
        val user = UserDto(email = data.email, password = data.password, nickname = data.nickname, sex = data.sex, age = data.age)
        return ResponseDto<Long>(data = userService.createUser(user));
    }

    @GetMapping("/{userId}")
    fun getUserInfo(@PathVariable userId: Long) : ResponseDto<UserDto> {
        log.info("[getUserInfo] 유저 정보 조회 PK = {}", userId)
        return ResponseDto(data = userService.getUserInfo(userId));
    }

    @PostMapping("/login")
    fun login(@RequestBody data: UserDto.Login): ResponseDto<UserLoginDto> {
        log.info("[login] 로그인 email = {}", data.email)
        return ResponseDto<UserLoginDto> (data = userService.login(data))
    }

    @PostMapping("/refresh")
    fun getAccessToken(@RequestBody token: TokenDto): ResponseDto<TokenDto> {
        log.info("[getAccessToken] access token 재발급")
        return ResponseDto<TokenDto>(data = userService.getAccessToken(token.refreshToken))
    }
}