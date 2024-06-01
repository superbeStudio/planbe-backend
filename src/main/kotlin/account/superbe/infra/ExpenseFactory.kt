package account.superbe.infra

import account.superbe.application.dto.ExpenseDto
import account.superbe.domain.model.Expense
import org.springframework.stereotype.Service

@Service
class ExpenseFactory {
    fun create(data: ExpenseDto, userSequence: Long): Expense {
        return Expense(categorySequence = data.categorySequence, expenseName = data.expenseName, expenseAmount = data.expenseAmount,
                userSequence = userSequence)
    }
}