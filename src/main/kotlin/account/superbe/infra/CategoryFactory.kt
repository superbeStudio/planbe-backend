package account.superbe.infra

import account.superbe.application.dto.CategoryDto
import account.superbe.domain.model.Category
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CategoryFactory(private val categoryRepo: CategoryJpaRepository) {
    val log: Logger = LoggerFactory.getLogger(CategoryFactory::class.java)
    fun create(data: CategoryDto, userSequence: Long): Category {
        if(categoryRepo.existsByUserSequenceAndCategoryNameAndCategoryType(userSequence, data.categoryName, data.categoryType)) {
            log.info("[createCategory] 카테고리 생성 실패. 이미 등록된 카테고리. 사용자 PK = {}, 카테고리 유형 = {}", userSequence, data.categoryType.name)
            throw IllegalArgumentException("이미 등록된 카테고리 명입니다.")
        }
        return Category(categoryName = data.categoryName, categoryType = data.categoryType, userSequence = userSequence, )
    }
}