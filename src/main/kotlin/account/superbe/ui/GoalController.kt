package account.superbe.ui

import account.superbe.application.GoalApplicationService
import account.superbe.application.dto.GoalDto
import account.superbe.common.io.ResponseDto
import account.superbe.ui.post.GoalPostRequest
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import lombok.RequiredArgsConstructor
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/goal")
@RequiredArgsConstructor
@Validated
class GoalController (private val goalService: GoalApplicationService){
    val log: Logger = LoggerFactory.getLogger(GoalController::class.java)
    @PostMapping
    fun createGoal(@RequestBody data: GoalPostRequest) : ResponseDto<Long>{
        log.info("[createGoal] 목표 생성 = {}", data.goalName)
        val goalDto = GoalDto(goalName = data.goalName, goalCategory = data.goalCategory, goalAmount = data.goalAmount, priority = data.priority, goalTime = data.goalTime, goalUrl = data.goalUrl)
        return ResponseDto<Long>(data = goalService.createGoal(goalDto));
    }
}