package account.superbe.infra

import account.superbe.domain.model.Expense
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Meta
import java.util.*

interface ExpenseJpaRepository : JpaRepository<Expense, Long> {
    @Meta(comment = "find All expense by user")
    fun findByUserSequence(userSequence: Long): List<Expense>

    @Meta(comment = "find one expense by user")
    fun findByExpenseSequenceAndUserSequence(expenseSeq: Long, userSeq: Long): Optional<Expense>

    @Meta(comment = "delete one expense by user")
    fun deleteByExpenseSequenceAndUserSequence(expenseSequence: Long, userSequence: Long): Int
}