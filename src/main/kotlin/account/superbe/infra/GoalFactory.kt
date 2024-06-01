package account.superbe.infra

import account.superbe.application.dto.GoalDto
import account.superbe.domain.model.Goal
import org.springframework.stereotype.Service

@Service
class GoalFactory {
    fun create(data: GoalDto, userSequence: Long): Goal {
        return Goal(goalName = data.goalName, categorySequence = data.categorySequence, goalAmount = data.goalAmount,
                priority = data.priority, goalTime = data.goalTime, goalUrl = data.goalUrl, userSequence = userSequence)
    }
}