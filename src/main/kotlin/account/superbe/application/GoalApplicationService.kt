package account.superbe.application

import account.superbe.application.dto.GoalDto
import account.superbe.infra.GoalFactory
import account.superbe.infra.GoalJpaRepository
import account.superbe.infra.UserFactory
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class GoalApplicationService(
        private val goalFactory: GoalFactory,
        private val goalRepo: GoalJpaRepository
) {
    val log: Logger = LoggerFactory.getLogger(GoalApplicationService::class.java)

    fun createGoal(data: GoalDto): Long {
        val goal = goalFactory.create(data)
        goalRepo.save(goal)
        log.info("[createGoal] 목표생성 성공 PK = {}", goal.seq)
        return goal.seq!!
    }

}