package account.superbe.application.dto

import account.superbe.domain.model.CategoryType
import java.time.LocalDateTime

class CategoryDto(
        val categorySequence: Long? = null,
        var categoryName: String,
        var categoryType: CategoryType,
        val userSequence: Long? = null,
        var createDatetime: LocalDateTime? = null,
        val updateDatetime: LocalDateTime? = null
) {

}
