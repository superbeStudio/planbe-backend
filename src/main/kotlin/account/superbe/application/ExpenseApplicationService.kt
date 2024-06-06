package account.superbe.application

import account.superbe.application.dto.ExpenseDto
import account.superbe.domain.model.CategoryType
import account.superbe.domain.service.CategoryService
import account.superbe.domain.service.ExpenseService
import account.superbe.domain.service.user.UserService
import account.superbe.infra.ExpenseFactory
import account.superbe.infra.ExpenseJpaRepository
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class ExpenseApplicationService(
        private val expenseService: ExpenseService,
        private val userService: UserService,
        private val categoryService: CategoryService,
        private val expenseFactory: ExpenseFactory,
        private val expenseJpaRepository: ExpenseJpaRepository
) {
    val log: Logger = LoggerFactory.getLogger(ExpenseApplicationService::class.java)

    @Transactional
    fun createExpense(email: String, data: ExpenseDto): Long {
        val user = userService.getUserByEmailNonNull(email)
        categoryService.validateCategorySequence(categorySequence = data.categorySequence, userSeq = user.userSeq!!, categoryType = CategoryType.E)
        val expense = expenseFactory.create(data, user.userSeq)
        expenseJpaRepository.save(expense)
        log.info("[createExpense] 지출 생성 완료 지출 PK = {}, 사용자 PK = {}", expense.expenseSequence, expense.userSequence)
        return expense.expenseSequence!!
    }

    @Transactional(readOnly = true)
    fun getExpenses(email: String): List<ExpenseDto> {
        val user = userService.getUserByEmailNonNull(email)
        val expenses = expenseJpaRepository.findByUserSequence(user.userSeq!!);
        val categoriesInfoMap = categoryService.getCategoriesNameMap(expenses
                .map { it -> it.categorySequence })

        val result = expenses.map { expense ->
            val categoryName = categoriesInfoMap.get(expense.categorySequence)
            ExpenseDto(expenseSequence = expense.expenseSequence, expenseName = expense.expenseName,
                    categorySequence = expense.categorySequence, expenseAmount = expense.expenseAmount,
                    createDatetime = expense.createDatetime, updateDatetime = expense.updateDatetime, categoryName = categoryName)
        }
        return result.sortedWith( // 정렬 조건 미정
                compareBy { it.createDatetime }
        )

    }

    @Transactional(readOnly = true)
    fun getExpense(email: String, expenseSeq: Long): ExpenseDto {
        val user = userService.getUserByEmailNonNull(email)
        val expense = expenseService.getExpenseDtoByExpenseSequenceAndUserSequenceNonNull(
                expenseSeq, user.userSeq!!)
        val categoryName = categoryService.getCategoryName(expense.categorySequence)
        return ExpenseDto(expenseSequence = expense.expenseSequence, expenseName = expense.expenseName,
                categorySequence = expense.categorySequence, expenseAmount = expense.expenseAmount,
                createDatetime = expense.createDatetime, updateDatetime = expense.updateDatetime, categoryName = categoryName)
    }

    @Transactional
    fun updateExpense(email: String, expenseSeq: Long, categorySequence: Long?, expenseName: String?, expenseAmount: Int?)
            : ExpenseDto {
        val user = userService.getUserByEmailNonNull(email)
        categorySequence?.also { categoryService.validateCategorySequence(categorySequence, user.userSeq!!, CategoryType.E) }
        return expenseService.updateExpense(user.userSeq!!, expenseSeq, categorySequence, expenseName, expenseAmount)
    }

    fun deleteExpense(email: String, expenseSeq: Long) {
        val user = userService.getUserByEmailNonNull(email)
        val cnt = expenseJpaRepository.deleteByExpenseSequenceAndUserSequence(expenseSeq, user.userSeq!!)
        if (cnt == 0) {
            log.info("[deleteExpense] 삭제할 데이터 없음, 지출 PK = {}, user PK = {}", expenseSeq, user.userSeq)
            throw IllegalArgumentException("해당 목표를 삭제할 수 없습니다. 목표가 존재하지않거나 본인이 작성한 목표가 아닙니다.")
        }
        log.info("[deleteExpense] 지출 PK = {}, 사용자 PK = {}, 총 {}개 삭제 완료 ", expenseSeq, user.userSeq, cnt)
    }

}