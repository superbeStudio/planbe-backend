package account.superbe.domain.service

import account.superbe.application.dto.GoalDto
import account.superbe.infra.GoalJpaRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class GoalService(private val goalRepo: GoalJpaRepository) {
    val log: Logger = LoggerFactory.getLogger(GoalService::class.java)
    fun getGoalBySequenceAndUserSequenceNonNull(goalSequence: Long, userSequence: Long): GoalDto {
        val goal = goalRepo.findByGoalSequenceAndUserSequence(goalSequence, userSequence) .orElseThrow {
            log.info("[getGoal] 목표 조회 실패. 목표 PK = {}, 유저 PK = {}", goalSequence, userSequence)
            throw IllegalArgumentException("해당 목표를 조회할 수 없습니다.")
        }
        log.info("[getGoal] 목표 조회 완료. 목표 PK = {}, 유저 PK = {}", goalSequence, userSequence)
        return GoalDto(goalSequence = goal.goalSequence, goalName = goal.goalName, goalCategory = goal.goalCategory,
                goalAmount = goal.goalAmount, priority = goal.priority, goalTime = goal.goalTime, goalUrl = goal.goalUrl,
                createDatetime = goal.createDatetime, updateDatetime = goal.updateDatetime)
    }

}
