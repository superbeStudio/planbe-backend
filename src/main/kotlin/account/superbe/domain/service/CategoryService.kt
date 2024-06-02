package account.superbe.domain.service

import account.superbe.application.dto.CategoryDto
import account.superbe.domain.model.Category
import account.superbe.domain.model.CategoryType
import account.superbe.infra.CategoryFactory
import account.superbe.infra.CategoryJpaRepository
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class CategoryService(private val categoryRepo: CategoryJpaRepository, private val categoryFactory: CategoryFactory) {
    val log: Logger = LoggerFactory.getLogger(CategoryService::class.java)
    fun updateCategory(userSeq: Long, categorySequence: Long, categoryType: CategoryType?, categoryName: String?): CategoryDto {
        val category = categoryRepo.findByUserSequenceAndCategorySequence(userSeq, categorySequence)
                .orElseThrow {
                    log.info("[updateCategory] 카테고리 수정 실패. PK오류이거나 본인이 작성한 카테고리가 아님. 카테고리 PK = {}, 사용자 PK = {}", categorySequence, userSeq)
                    throw IllegalArgumentException("해당 카테고리를 수정할 수 없습니다. 없는 카테고리 번호이거나 본인이 작성한 카테고리가 아닙니다.")
                }
        val finalCategoryType = categoryType ?: category.categoryType
        val finalCategoryName = categoryName ?: category.categoryName
        if (categoryRepo.existsByUserSequenceAndCategoryTypeAndCategorySequenceNotAndCategoryName(userSeq,
                        finalCategoryType, categorySequence, finalCategoryName)) {
            log.info("[updateCategory] 카테고리 수정 실패. 이미 등록된 카테고리 명입니다. 카테고리 PK = {}, 사용자 PK = {}", categorySequence, userSeq)
            throw IllegalArgumentException("다른 카테고리에서 사용중인 카테고리 명을 입력하셨습니다.")
        }
        category.updateData(categoryType, categoryName)
        categoryRepo.save(category)
        return CategoryDto(categorySequence = category.categorySequence, categoryName = category.categoryName,
                categoryType = category.categoryType, userSequence = category.userSequence,
                createDatetime = category.createDatetime, updateDatetime = category.updateDatetime)
    }
}