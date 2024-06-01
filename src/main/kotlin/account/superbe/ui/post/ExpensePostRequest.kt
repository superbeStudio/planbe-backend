package account.superbe.ui.post

class ExpensePostRequest(
    val categorySequence: Long,
    val expenseName: String,
    val expenseAmount: Int
) {
    class UpdateExpense (
            val categorySequence: Long? = null,
            val expenseName: String? = null,
            val expenseAmount: Int? = null
    ) {
    }
}