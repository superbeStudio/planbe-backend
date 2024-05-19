package account.superbe.ui

import account.superbe.application.GoalApplicationService
import account.superbe.application.dto.GoalDto
import account.superbe.common.io.ResponseDto
import account.superbe.domain.service.GoalService
import account.superbe.ui.post.GoalPostRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import lombok.RequiredArgsConstructor
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/goal")
@RequiredArgsConstructor
@Validated
class GoalController (private val goalAppService: GoalApplicationService, private val goalService: GoalService){
    val log: Logger = LoggerFactory.getLogger(GoalController::class.java)
    @PostMapping
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun createGoal(@RequestBody data: GoalPostRequest, @AuthenticationPrincipal user: UserDetails) : ResponseDto<Long>{
        log.info("[createGoal] 목표 생성 user seq = {}", user.username)
        val goalDto = GoalDto(goalName = data.goalName, goalCategory = data.goalCategory, goalAmount = data.goalAmount, priority = data.priority, goalTime = data.goalTime, goalUrl = data.goalUrl)
        return ResponseDto(data = goalAppService.createGoal(goalDto, user.username));
    }

    @GetMapping("/{goalSeq}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getGoal(@PathVariable goalSeq: Long, @AuthenticationPrincipal user: UserDetails): ResponseDto<GoalDto> {
        log.info("[getGoal - single] 목표 상세 조회 PK = {}, userEmail = {}", goalSeq, user.username)
        return ResponseDto(data = goalAppService.getGoal(goalSeq, user.username))
    }

    @GetMapping
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getGoals(@AuthenticationPrincipal user: UserDetails): ResponseDto<List<GoalDto>> {
        log.info("[getGoals] 목표 상세 조회. userEmail = {}", user.username)
        return ResponseDto(data = goalAppService.getGoals(user.username))
    }
}