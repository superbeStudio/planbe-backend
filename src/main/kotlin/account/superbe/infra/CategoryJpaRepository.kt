package account.superbe.infra

import account.superbe.domain.model.Category
import account.superbe.domain.model.CategoryType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Meta
import java.util.*

interface CategoryJpaRepository : JpaRepository<Category, Long> {
    @Meta(comment = "check for duplicates in my category name and type")
    fun existsByUserSequenceAndCategoryNameAndCategoryType(userSequence: Long, categoryName: String, categoryType: CategoryType): Boolean

    @Meta(comment = "find All by user Sequence and category Type")
    fun findByUserSequenceAndCategoryType(userSequence: Long, categoryType: CategoryType): List<Category>
    
    @Meta(comment = "delete one category by user")
    fun deleteByCategorySequenceAndUserSequence(categorySequence: Long, userSequence: Long): Int

    @Meta(comment = "find one category by user and category PK")
    fun findByUserSequenceAndCategorySequence(userSequence: Long, categorySequence: Long): Optional<Category>

    @Meta(comment = "check for duplicates in my category name")
    fun existsByUserSequenceAndCategoryTypeAndCategorySequenceNotAndCategoryName(userSequence: Long, categoryType: CategoryType, categorySequence: Long, categoryName: String): Boolean

    @Meta(comment = "check if the category sequence is the user")
    fun existsByUserSequenceAndCategorySequenceAndCategoryType(userSeq: Long, categorySequence: Long, categoryType: CategoryType): Boolean
}