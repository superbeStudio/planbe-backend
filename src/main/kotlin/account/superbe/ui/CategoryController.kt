package account.superbe.ui

import account.superbe.application.CategoryApplicationService
import account.superbe.application.dto.CategoryDto
import account.superbe.common.io.ResponseDto
import account.superbe.domain.model.CategoryType
import account.superbe.ui.post.CategoryPostRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cate")
@RequiredArgsConstructor
class CategoryController(private val categoryApplicationService: CategoryApplicationService) {
    val log: Logger = LoggerFactory.getLogger(CategoryController::class.java)

    @PostMapping
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun createCategory(@AuthenticationPrincipal user: UserDetails, @RequestBody data: CategoryPostRequest): ResponseDto<Long> {
        log.info("[createCategory] 카테고리 생성. 사용자 email = {}, 카테고리 타입 = {}", user.username, data.categoryType.name)
        return ResponseDto(data = categoryApplicationService.createCategory(user.username, CategoryDto(
                categoryType = data.categoryType, categoryName = data.categoryName)))
    }

    @GetMapping("/goal")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getGoalCategories(@AuthenticationPrincipal user: UserDetails): ResponseDto<List<CategoryDto>> {
        log.info("[getGoalCategories] 목표 카테고리 전체 조회. email = {}", user.username)
        return ResponseDto(data = categoryApplicationService.getCategories(user.username, CategoryType.G))
    }

    @GetMapping("/expense")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getExpenseCategories(@AuthenticationPrincipal user: UserDetails): ResponseDto<List<CategoryDto>> {
        log.info("[getExpenseCategories] 지출 카테고리 전체 조회. email = {}", user.username)
        return ResponseDto(data = categoryApplicationService.getCategories(user.username, CategoryType.E))
    }

    @DeleteMapping("/{categorySequence}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun deleteCategory(@AuthenticationPrincipal user: UserDetails, @PathVariable categorySequence: Long): ResponseDto<Nothing> {
        log.info("[deleteCategory] 카테고리 삭제 카테고리 PK = {}, email = {}", categorySequence, user.username)
        categoryApplicationService.deleteCategory(user.username, categorySequence)
        return ResponseDto()
    }

    @PutMapping("/{categorySequence}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun updateCategory(@AuthenticationPrincipal user: UserDetails, @PathVariable categorySequence: Long,
                       @RequestBody data: CategoryPostRequest.CategoryUpdate): ResponseDto<CategoryDto> {
        log.info("[updateCategory] 카테고리 삭제 카테고리 PK = {}, email = {}", categorySequence, user.username)
        return ResponseDto(data = categoryApplicationService.updateCategory(
                user.username, categorySequence, data.categoryType, data.categoryName))
    }
}