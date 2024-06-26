package account.superbe.application.dto

import account.superbe.domain.model.Gen
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import java.time.LocalDateTime

class UserDto(
        val userSeq: Long? = null,
        val email: String,
        @JsonIgnore
        val password: String? = null,
        val nickname: String,
        val sex: Gen,
        val birth: LocalDate,
        var currencyMain: String? = null,
        var modeScreen: String? = null,
        val createDate: LocalDateTime? = null,
        var updateDate: LocalDateTime? = null,
) {
}