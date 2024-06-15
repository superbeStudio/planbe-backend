package account.superbe.application

import account.superbe.application.dto.CategoryDto
import account.superbe.domain.model.CategoryType
import account.superbe.domain.service.CategoryService
import account.superbe.domain.service.user.UserService
import account.superbe.infra.CategoryFactory
import account.superbe.infra.CategoryJpaRepository
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class CategoryApplicationService(private val userService: UserService, private val categoryFactory: CategoryFactory,
                                 private val categoryRepo: CategoryJpaRepository, private val categoryService: CategoryService) {
    val log: Logger = LoggerFactory.getLogger(CategoryApplicationService::class.java)

    @Transactional
    fun createCategory(email: String, data: CategoryDto): Long {
        val user = userService.getUserByEmailNonNull(email)
        val category = categoryFactory.create(data, user.userSeq!!)
        categoryRepo.save(category)
        log.info("[createCategory] 카테고리 생성 완료 카테고리 PK = {}, 카테고리 유형 = {}, 사용자 PK = {}",
                category.categorySequence, category.categoryType.name, category.userSequence)
        return category.categorySequence!!
    }

    @Transactional(readOnly = true)
    fun getCategories(email: String, categoryType: CategoryType): List<CategoryDto> {
        val user = userService.getUserByEmailNonNull(email)
        val categories = categoryRepo.findByUserSequenceAndCategoryType(user.userSeq!!, categoryType)
        val result = categories.map { cate ->
            CategoryDto(categorySequence = cate.categorySequence, categoryName = cate.categoryName,
                    categoryType = cate.categoryType, userSequence = cate.userSequence,
                    createDatetime = cate.createDatetime, updateDatetime = cate.updateDatetime)
        }
        return result.sortedWith( // 정렬 조건 미정
                compareBy { it.createDatetime }
        )
    }

    @Transactional
    fun deleteCategory(email: String, categorySequence: Long) {
        val user = userService.getUserByEmailNonNull(email)
        val cnt = categoryRepo.deleteByCategorySequenceAndUserSequence(categorySequence, user.userSeq!!)
        if (cnt < 1) {
            log.info("[deleteCategory] 삭제할 데이터 없음, 카테고리 PK = {}, user PK = {}", categorySequence, user.userSeq)
            throw IllegalArgumentException("해당 카테고리를 삭제할 수 없습니다. 카테고리 PK 오류이거나 본인이 작성한 카테고리가 아닙니다.")
        }
        log.info("[deleteExpense] 카테고리 PK = {}, 사용자 PK = {}, 총 {}개 삭제 완료 ", categorySequence, user.userSeq, cnt)
    }

    fun updateCategory(email: String, categorySequence: Long, categoryType: CategoryType?, categoryName: String?)
            : CategoryDto {
        val user = userService.getUserByEmailNonNull(email)
        return categoryService.updateCategory(user.userSeq!!, categorySequence, categoryType, categoryName)
    }
}
