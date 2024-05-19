package account.superbe.application

import account.superbe.application.dto.GoalDto
import account.superbe.domain.service.GoalService
import account.superbe.domain.service.UserService
import account.superbe.infra.GoalFactory
import account.superbe.infra.GoalJpaRepository
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class GoalApplicationService(
        private val goalRepo: GoalJpaRepository,
        private val goalService: GoalService,
        private val userService: UserService,
        private val goalFactory: GoalFactory
) {
    val log: Logger = LoggerFactory.getLogger(GoalApplicationService::class.java)

    @Transactional
    fun createGoal(data: GoalDto, email: String): Long {
        val user = userService.getUserByEmailNonNull(email)
        val goal = goalFactory.create(data, user.userSeq!!)
        goalRepo.save(goal)
        log.info("[createGoal] 목표생성 성공 PK = {}", goal.goalSequence)
        return goal.goalSequence!!
    }

    @Transactional(readOnly = true)
    fun getGoal(goalSeq: Long, email: String): GoalDto {
        val user = userService.getUserByEmailNonNull(email);
        return goalService.getGoalBySequenceAndUserSequenceNonNull(goalSeq, user.userSeq!!)
    }

    @Transactional
    fun getGoals(email: String): List<GoalDto> {
        val user = userService.getUserByEmailNonNull(email)
        val goals = goalRepo.findAllByUserSequence(user.userSeq!!);
        log.info("[getGoals] 유저의 모든 목표 조회 size = {}", goals.size)
        val result = goals.map { goal ->
            return@map GoalDto(goalSequence = goal.goalSequence, goalName = goal.goalName, goalCategory = goal.goalCategory,
                    goalAmount = goal.goalAmount, priority = goal.priority, goalTime = goal.goalTime, goalUrl = goal.goalUrl,
                    createDatetime = goal.createDatetime, updateDatetime = goal.updateDatetime)
        }

        return result.sortedWith( //우선순위 정렬
                compareBy { it.priority }
        )
    }

}