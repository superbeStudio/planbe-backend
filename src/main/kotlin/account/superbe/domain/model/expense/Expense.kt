package account.superbe.domain.model.expense

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "expense_info")
class Expense (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    val uuid: Long? = null,
    @Column(name = "category")
    var category: String,
    @Column(name = "expense_name")
    var expenseName: String,
    @Column(name = "expense_amount")
    var expenseAmount: Int,
    @Column(name = "create_datetime")
    var createDatetime: LocalDateTime,
    @Column(name = "update_datetime")
    var updateDatetime: LocalDateTime,
){
}