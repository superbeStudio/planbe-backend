package account.superbe.ui.post

import account.superbe.application.dto.GoalDto
import java.time.LocalDate

class GoalPostRequest (
    val goalName: String,
    val goalCategory: String,
    val goalAmount: Int,
    val priority: Int,
    val goalTime: LocalDate,
    val goalUrl: String
){
    class Update(
            val goalName: String? =null,
            val goalCategory: String? =null,
            val goalAmount: Int? =null,
            val priority: Int? =null,
            val goalTime: LocalDate? =null,
            val goalUrl: String? =null
    ) {

    }
}