package account.superbe.ui.post

import account.superbe.domain.model.CategoryType

class CategoryPostRequest(
        val categoryName: String,
        val categoryType: CategoryType
) {
    class CategoryUpdate(
            val categoryName: String? = null,
            val categoryType: CategoryType? = null
    ) {

    }
}