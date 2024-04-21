package account.superbe.ui

import account.superbe.application.UserApplicationService
import account.superbe.application.dto.UserDto
import account.superbe.common.io.ResponseDto
import account.superbe.ui.post.UserLoginDto
import account.superbe.ui.post.UserPostRequest
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
class UserController() {
    @Autowired
    private lateinit var userService: UserApplicationService

    @PostMapping
    fun createUser(@RequestBody data: UserPostRequest): ResponseDto<Long> {
        val user = UserDto(email = data.email, password = data.password, nickname = data.nickname, sex = data.sex, age = data.age)
        return ResponseDto<Long>(data = userService.createUser(user));
    }

    @GetMapping("/{userId}")
    fun getUserInfo(@PathVariable userId: Long) : ResponseDto<UserDto> {
        return ResponseDto(data = userService.getUserInfo(userId));
    }

    @PostMapping("/login")
    fun login(@RequestBody data: UserDto.Login): ResponseDto<UserLoginDto> {
        return ResponseDto<UserLoginDto> (data = userService.login(data))
    }
}