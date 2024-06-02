package account.superbe.application.dto

import java.time.LocalDate
import java.time.LocalDateTime

class GoalDto(
        val goalSequence: Long? = null,
        var goalName: String,
        var categorySequence: Long,
        var categoryName: String? = null,
        var goalAmount: Int,
        var priority: Int,
        var goalTime: LocalDate,
        var goalUrl: String,
        val createDatetime: LocalDateTime? = null,
        var updateDatetime: LocalDateTime? = null
) {
}