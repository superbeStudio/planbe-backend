package account.superbe.ui

import account.superbe.application.UserApplicationService
import account.superbe.application.dto.UserDto
import account.superbe.common.io.ResponseDto
import account.superbe.domain.service.UserService
import account.superbe.security.TokenDto
import account.superbe.application.dto.UserLoginDto
import account.superbe.ui.post.UserPostRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
class UserController(private val userAppService: UserApplicationService, private val userService: UserService) {
    val log: Logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping
    fun createUser(@RequestBody data: UserPostRequest): ResponseDto<Long> {
        log.info("[createUser] 회원가입 email = {}", data.email)
        val user = UserDto(email = data.email, password = data.password, nickname = data.nickname, sex = data.sex, birth = data.birth)
        return ResponseDto<Long>(data = userService.createUser(user));
    }

    @GetMapping
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getUserInfo(@AuthenticationPrincipal user: UserDetails) : ResponseDto<UserDto> {
        log.info("[getUserInfo] 유저 정보 조회 email = {}", user.username)
        val result = userService.getUserByEmailNonNull(user.username)
        log.info("[getUserInfo] user PK = {}", result.userSeq)
        return ResponseDto(data = result);
    }

    @PostMapping("/login")
    fun login(@RequestBody data: UserPostRequest.Login): ResponseDto<UserLoginDto> {
        log.info("[login] 로그인 email = {}", data.email)
        return ResponseDto<UserLoginDto> (data = userAppService.login(data.email, data.password))
    }

    @PostMapping("/refresh")
    fun getAccessToken(@RequestBody token: TokenDto): ResponseDto<TokenDto> {
        log.info("[getAccessToken] access token 재발급")
        return ResponseDto<TokenDto>(data = userAppService.getAccessToken(token.refreshToken))
    }
}