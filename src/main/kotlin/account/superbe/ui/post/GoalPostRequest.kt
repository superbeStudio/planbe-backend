package account.superbe.ui.post

import java.time.LocalDate

class GoalPostRequest (
    val goalName: String,
    val goalCategory: String,
    val goalAmount: Int,
    val priority: Int,
    val goalTime: LocalDate,
    val goalUrl: String
){
}