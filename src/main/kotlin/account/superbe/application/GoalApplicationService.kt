package account.superbe.application

import account.superbe.application.dto.GoalDto
import account.superbe.domain.model.CategoryType
import account.superbe.domain.service.CategoryService
import account.superbe.domain.service.GoalService
import account.superbe.domain.service.UserService
import account.superbe.infra.GoalFactory
import account.superbe.infra.GoalJpaRepository
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@RequiredArgsConstructor
class GoalApplicationService(
        private val goalRepo: GoalJpaRepository,
        private val goalService: GoalService,
        private val userService: UserService,
        private val categoryService: CategoryService,
        private val goalFactory: GoalFactory
) {
    val log: Logger = LoggerFactory.getLogger(GoalApplicationService::class.java)

    @Transactional
    fun createGoal(data: GoalDto, email: String): Long {
        val user = userService.getUserByEmailNonNull(email)
        categoryService.validateCategorySequence(data.categorySequence, user.userSeq!!, CategoryType.G)
        val goal = goalFactory.create(data, user.userSeq)
        goalRepo.save(goal)
        log.info("[createGoal] 목표생성 성공 PK = {}", goal.goalSequence)
        return goal.goalSequence!!
    }

    @Transactional(readOnly = true)
    fun getGoal(goalSeq: Long, email: String): GoalDto {
        val user = userService.getUserByEmailNonNull(email);
        val result = goalService.getGoalDtoBySequenceAndUserSequenceNonNull(goalSeq, user.userSeq!!)
        val categoryName = categoryService.getCategoryName(result.categorySequence)
        result.categoryName = categoryName
        return result
    }

    @Transactional(readOnly = true)
    fun getGoals(email: String): List<GoalDto> {
        val user = userService.getUserByEmailNonNull(email)
        val goals = goalRepo.findAllByUserSequence(user.userSeq!!);
        log.info("[getGoals] 유저(PK = {})의 모든 목표 조회 size = {}", user.userSeq, goals.size)
        val categoriesNameMap = categoryService.getCategoriesNameMap(goals.map { it -> it.categorySequence })
        val result = goals.map { goal ->
            val categoryName = categoriesNameMap.get(goal.categorySequence)
            GoalDto(goalSequence = goal.goalSequence, goalName = goal.goalName, categorySequence = goal.categorySequence, categoryName = categoryName,
                    goalAmount = goal.goalAmount, priority = goal.priority, goalTime = goal.goalTime, goalUrl = goal.goalUrl,
                    createDatetime = goal.createDatetime, updateDatetime = goal.updateDatetime)
        }

        return result.sortedWith( //우선순위 정렬
                compareBy { it.priority }
        )
    }

    @Transactional
    fun deleteGoal(goalSeq: Long, email: String) {
        val user = userService.getUserByEmailNonNull(email)
        val cnt = goalRepo.deleteByGoalSequenceAndUserSequence(goalSeq, user.userSeq!!)
        if (cnt == 0) {
            log.info("[deleteGoal] 삭제할 데이터 없음, 목표 PK = {}, user PK = {}", goalSeq, user.userSeq)
            throw IllegalArgumentException("해당 목표를 삭제할 수 없습니다. 목표가 존재하지않거나 본인이 작성한 목표가 아닙니다.")
        }
        log.info("[deleteGoal] 목표 PK = {}, 사용자 PK = {}, 총 {}개 삭제 완료 ", goalSeq, user.userSeq, cnt)
    }

    @Transactional
    fun updateGoalInfo(
            goalSequence: Long, email: String, goalName: String?, categorySequence: Long?, goalAmount: Int?, priority: Int?,
            goalTime: LocalDate?, goalUrl: String?) {
        val user = userService.getUserByEmailNonNull(email)
        categorySequence?.also { categoryService.validateCategorySequence(categorySequence, user.userSeq!!, CategoryType.G) }
        goalService.updateGoalInfo(goalSequence, user.userSeq!!, goalName, categorySequence, goalAmount, priority, goalTime, goalUrl);
    }

}