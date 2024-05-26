package account.superbe.infra

import account.superbe.domain.model.Expense
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Meta
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

interface ExpenseJpaRepository : JpaRepository<Expense, Long> {
    @Meta(comment = "find All expense by user")
    fun findByUserSequence(userSequence: Long): List<Expense>
    @Meta(comment = "find one expense by user")
    fun findByExpenseSequenceAndUserSequence(expenseSeq: Long, userSeq: Long): Optional<Expense>

    @Meta(comment = "delete one expense by user")
    @Modifying
    @Transactional
    @Query("""
        delete from Expense 
        where expenseSequence = :expenseSequence and userSequence = :userSequence
    """)
    fun deleteByExpenseSequenceAndUserSequence(expenseSequence: Long, userSequence: Long): Int
}