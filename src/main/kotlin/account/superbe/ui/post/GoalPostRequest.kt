package account.superbe.ui.post

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

class GoalPostRequest(
        @Schema(description = "목표 명")
        val goalName: String,
        @Schema(description = "카테고리 PK")
        val categorySequence: Long,
        @Schema(description = "목표 금액")
        val goalAmount: Int,
        @Schema(description = "우선순위")
        val priority: Int,
        @Schema(description = "목표기한")
        val goalTime: LocalDate,
        @Schema(description = "목표 url")
        val goalUrl: String
) {
    class Update(
            @Schema(description = "목표 명")
            val goalName: String? = null,
            @Schema(description = "카테고리 PK")
            val categorySequence: Long? = null,
            @Schema(description = "목표 금액")
            val goalAmount: Int? = null,
            @Schema(description = "우선순위")
            val priority: Int? = null,
            @Schema(description = "목표기한")
            val goalTime: LocalDate? = null,
            @Schema(description = "목표 url")
            val goalUrl: String? = null
    ) {

    }
}