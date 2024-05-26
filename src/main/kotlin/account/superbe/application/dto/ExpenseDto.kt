package account.superbe.application.dto

import java.time.LocalDateTime

class ExpenseDto(
    val expenseSequence: Long? = null,
    var category: String,
    var expenseName: String,
    var expenseAmount: Int,
    var createDatetime: LocalDateTime? = null,
    var updateDatetime: LocalDateTime? = null
) {
}