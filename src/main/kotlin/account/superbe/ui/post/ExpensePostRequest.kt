package account.superbe.ui.post

import io.swagger.v3.oas.annotations.media.Schema

class ExpensePostRequest(
        @Schema(description = "카테고리 PK")
        val categorySequence: Long,
        @Schema(description = "지출 명")
        val expenseName: String,
        @Schema(description = "지출 금액")
        val expenseAmount: Int
) {
    class UpdateExpense(
            @Schema(description = "카테고리 PK")
            val categorySequence: Long? = null,
            @Schema(description = "지출 명")
            val expenseName: String? = null,
            @Schema(description = "지출 금액")
            val expenseAmount: Int? = null
    ) {
    }
}