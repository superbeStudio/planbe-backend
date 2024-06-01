package account.superbe.ui.post

import account.superbe.application.dto.GoalDto
import java.time.LocalDate

class GoalPostRequest (
    val goalName: String,
    val categorySequence: Long,
    val goalAmount: Int,
    val priority: Int,
    val goalTime: LocalDate,
    val goalUrl: String
){
    class Update(
            val goalName: String? =null,
            val categorySequence: Long? =null,
            val goalAmount: Int? =null,
            val priority: Int? =null,
            val goalTime: LocalDate? =null,
            val goalUrl: String? =null
    ) {

    }
}