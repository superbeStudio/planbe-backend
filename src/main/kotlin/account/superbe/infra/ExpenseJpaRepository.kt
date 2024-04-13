package account.superbe.infra

import account.superbe.domain.model.Expense
import org.springframework.data.jpa.repository.JpaRepository

interface ExpenseJpaRepository : JpaRepository<Expense, Long> {
}