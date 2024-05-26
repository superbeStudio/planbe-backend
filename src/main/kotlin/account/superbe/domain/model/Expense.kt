package account.superbe.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.cglib.core.Local
import org.springframework.util.StringUtils
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "expense_info")
class Expense (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_seq")
    val expenseSequence: Long? = null,
    @Column(name = "category")
    var category: String,
    @Column(name = "expense_name")
    var expenseName: String,
    @Column(name = "expense_amount")
    var expenseAmount: Int,
    @Column(name = "user_seq")
    var userSequence: Long,
    @Column(name = "create_datetime")
    var createDatetime: LocalDateTime = LocalDateTime.now(),
    @Column(name = "update_datetime")
    var updateDatetime: LocalDateTime = LocalDateTime.now()
){
    fun updateData(
            category: String?, expenseName: String?, expenseAmount: Int?
    ) {
        if(StringUtils.hasText(category)) {
            this.category = category!!
        }
        if(StringUtils.hasText(expenseName)) {
            this.expenseName = expenseName!!
        }
        expenseAmount?.also { this.expenseAmount = it }
        this.updateDatetime = LocalDateTime.now()
    }
}