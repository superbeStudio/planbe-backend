package account.superbe.domain.service

import account.superbe.application.dto.ExpenseDto
import account.superbe.domain.model.Expense
import account.superbe.infra.ExpenseJpaRepository
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class ExpenseService(private val expenseRepo: ExpenseJpaRepository) {
    val log: Logger = LoggerFactory.getLogger(ExpenseService::class.java)

    fun getExpenseDtoByExpenseSequenceAndUserSequenceNonNull(expenseSeq: Long, userSeq: Long): ExpenseDto {
        val expense = getExpenseEntityByExpenseSequenceAndUserSequenceNonNull(expenseSeq, userSeq)
        return ExpenseDto(expenseSequence = expense.expenseSequence, expenseName = expense.expenseName,
                category = expense.category, expenseAmount = expense.expenseAmount,
                createDatetime = expense.createDatetime, updateDatetime = expense.updateDatetime)

    }

    private fun getExpenseEntityByExpenseSequenceAndUserSequenceNonNull(expenseSeq: Long, userSeq: Long): Expense {
        val expense = expenseRepo.findByExpenseSequenceAndUserSequence(expenseSeq, userSeq).orElseThrow {
            log.info("[getExpense] 지출 조회 불가능. 지출 PK = {}, 사용자 PK = {}", expenseSeq, userSeq)
            throw IllegalArgumentException("지출 조회 실패. 본인이 작성한 지출이 아니거나 존자하지 않는 지출번호입니다.")
        }
        return expense
    }

    fun updateExpense(userSeq: Long, expenseSeq: Long, category: String?, expenseName: String?, expenseAmount: Int?)
    : ExpenseDto {
        val expense = getExpenseEntityByExpenseSequenceAndUserSequenceNonNull(expenseSeq, userSeq)
        expense.updateData(category, expenseName, expenseAmount)

        expenseRepo.save(expense)
        log.info("[updateExpense] 지출 수정 완료 지출 PK = {}, 사용자 PK = {}", expenseSeq, userSeq)
        return ExpenseDto(expenseSequence = expense.expenseSequence, expenseName = expense.expenseName,
                category = expense.category, expenseAmount = expense.expenseAmount,
                createDatetime = expense.createDatetime, updateDatetime = expense.updateDatetime)
    }

}