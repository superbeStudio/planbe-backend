package account.superbe.ui

import account.superbe.application.ExpenseApplicationService
import account.superbe.application.dto.ExpenseDto
import account.superbe.common.io.ResponseDto
import account.superbe.ui.post.ExpensePostRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
class ExpenseController(private val expenseService: ExpenseApplicationService) {
    val log: Logger = LoggerFactory.getLogger(ExpenseController::class.java)

    @PostMapping
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun createExpense(@RequestBody data: ExpensePostRequest, @AuthenticationPrincipal user: UserDetails): ResponseDto<Long> {
        log.info("[createExpense] 지출 생성. 사용자 email = {}", user.username)
        return ResponseDto(data = expenseService.createExpense(user.username, ExpenseDto(
                categorySequence = data.categorySequence, expenseName = data.expenseName, expenseAmount = data.expenseAmount)));
    }

    @GetMapping
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getExpenses(@AuthenticationPrincipal user: UserDetails) :ResponseDto<List<ExpenseDto>> {
        log.info("[getExpenses] 지출 목록 조회. 사용자 email = {}", user.username)
        return ResponseDto(data = expenseService.getExpenses(user.username))
    }

    @GetMapping("/{expenseSeq}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getExpense(
            @AuthenticationPrincipal user: UserDetails, @PathVariable expenseSeq: Long): ResponseDto<ExpenseDto> {
        log.info("[getExpense] 지출 조회. 사용자 email = {}, 지출 PK = {}", user.username, expenseSeq)
        return ResponseDto(data = expenseService.getExpense(user.username, expenseSeq))
    }

    @PostMapping("/{expenseSeq}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun updateExpense(
            @AuthenticationPrincipal user: UserDetails, @PathVariable expenseSeq: Long,
            @RequestBody data: ExpensePostRequest.UpdateExpense): ResponseDto<ExpenseDto> {
        log.info("[updateExpense] 지출 수정. 사용자 email = {}, 지출 PK = {}", user.username, expenseSeq)
        return ResponseDto(data = expenseService.updateExpense(user.username, expenseSeq, data.categorySequence, data.expenseName, data.expenseAmount))
    }

    @DeleteMapping("/{expenseSeq}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun deleteExpense(@AuthenticationPrincipal user: UserDetails, @PathVariable expenseSeq: Long): ResponseDto<Nothing> {
        log.info("[deleteExpense] 지출 수정. 사용자 email = {}, 지출 PK = {}", user.username, expenseSeq)
        expenseService.deleteExpense(user.username, expenseSeq)
        return ResponseDto()
    }

}