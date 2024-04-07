package account.superbe.infra.expense

import account.superbe.domain.model.expense.Expense
import org.springframework.data.jpa.repository.JpaRepository

interface ExpenseJpaRepository : JpaRepository<Expense, Long> {
}