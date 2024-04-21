package account.superbe.application.dto

import account.superbe.domain.model.Gen
import lombok.EqualsAndHashCode
import java.time.LocalDateTime

class UserDto(
    val uuid: Long? = null,
    val email: String,
    val password: String? = null,
    val nickname: String,
    val sex: Gen,
    val age: Int,
    var currencyMain: String? = null,
    var modeScreen: String? = null,
    val createDate: LocalDateTime? = null,
    var updateDate: LocalDateTime? = null,
) {
    class Login(
        val email: String,
        val password: String) {

    }
}