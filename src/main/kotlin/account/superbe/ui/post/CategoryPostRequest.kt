package account.superbe.ui.post

import account.superbe.domain.model.CategoryType
import io.swagger.v3.oas.annotations.media.Schema

class CategoryPostRequest(
        @Schema(description = "카테고리 명")
        val categoryName: String,
        @Schema(description = "카테고리 유형 - G[목표], E[지출]")
        val categoryType: CategoryType
) {
    class CategoryUpdate(
            @Schema(description = "카테고리 명")
            val categoryName: String? = null,
            @Schema(description = "카테고리 유형 - G[목표], E[지출]")
            val categoryType: CategoryType? = null
    ) {

    }
}