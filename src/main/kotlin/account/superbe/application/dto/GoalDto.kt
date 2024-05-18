package account.superbe.application.dto

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class GoalDto(
        val seq: Long? = null,
        var goalName: String,
        var goalCategory: String,
        var goalAmount: Int,
        var priority: Int,
        var goalTime: LocalDate,
        var goalUrl: String,
        val createDatetime: LocalDateTime? = null,
        var updateDatetime: LocalDateTime? = null
) {
}