package account.superbe.infra

import account.superbe.domain.model.Category
import account.superbe.domain.model.CategoryType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Meta
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface CategoryJpaRepository : JpaRepository<Category, Long> {
    @Meta(comment = "check for duplicates in my category name and type")
    fun existsByUserSequenceAndCategoryNameAndCategoryType(userSequence: Long, categoryName: String,
                                                           categoryType: CategoryType): Boolean

    @Meta(comment = "find All by user Sequence and category Type")
    fun findByUserSequenceAndCategoryType(userSequence: Long, categoryType: CategoryType): List<Category>

    @Meta(comment = "delete one category by user")
    @Modifying
    @Transactional
    @Query("""
        delete from Category 
        where categorySequence = :categorySequence and userSequence = :userSequence
    """)
    fun deleteByExpenseSequenceAndUserSequence(categorySequence: Long, userSequence: Long): Int

    @Meta(comment = "find one category by user and category PK")
    fun findByUserSequenceAndCategorySequence(userSequence: Long, categorySequence: Long): Optional<Category>

    fun existsByUserSequenceAndCategoryTypeAndCategorySequenceNotAndCategoryName(userSequence: Long, categoryType: CategoryType,
                                                                  categorySequence: Long, categoryName: String): Boolean
}