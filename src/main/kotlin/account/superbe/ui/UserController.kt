package account.superbe.ui

import account.superbe.application.UserApplicationService
import account.superbe.application.dto.UserDto
import account.superbe.common.io.ResponseDto
import account.superbe.ui.post.UserPostRequest
import lombok.RequiredArgsConstructor
import lombok.extern.java.Log
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
class UserController(private val userService: UserApplicationService) {

    @PostMapping
    fun createUser(@RequestBody data: UserPostRequest): ResponseDto<Long> {
        val user = UserDto(email = data.email, password = data.password, nickname = data.nickname, sex = data.sex, age = data.age)
        return ResponseDto<Long>(data = userService.createUser(user));
    }
}