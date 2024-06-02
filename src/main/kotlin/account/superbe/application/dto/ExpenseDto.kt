package account.superbe.application.dto

import java.time.LocalDateTime

class ExpenseDto(
        val expenseSequence: Long? = null,
        var categorySequence: Long,
        var categoryName: String? = null,
        var expenseName: String,
        var expenseAmount: Int,
        var createDatetime: LocalDateTime? = null,
        var updateDatetime: LocalDateTime? = null
) {
}