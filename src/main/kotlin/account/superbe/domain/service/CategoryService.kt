package account.superbe.domain.service

import account.superbe.application.dto.CategoryDto
import account.superbe.domain.model.CategoryType
import account.superbe.infra.CategoryJpaRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class CategoryService(private val categoryRepo: CategoryJpaRepository) {
    fun updateCategory(userSeq: Long, categorySequence: Long, categoryType: CategoryType?, categoryName: String?): CategoryDto {
        val category = categoryRepo.findByUserSequenceAndCategorySequence(userSeq, categorySequence)
        category.updateData(categoryType, categoryName)
        return CategoryDto(categorySequence = category.categorySequence, categoryName = category.categoryName,
                categoryType = category.categoryType, userSequence = category.userSequence,
                createDatetime = category.createDatetime, updateDatetime = category.updateDatetime)
    }
}