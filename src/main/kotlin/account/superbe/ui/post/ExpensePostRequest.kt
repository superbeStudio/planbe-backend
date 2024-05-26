package account.superbe.ui.post

class ExpensePostRequest(
    val category: String,
    val expenseName: String,
    val expenseAmount: Int
) {
    class Update (
            val category: String? = null,
            val expenseName: String? = null,
            val expenseAmount: Int? = null
    ) {
    }
}