package account.superbe.infra

import account.superbe.domain.model.Goal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Meta
import java.util.*

interface GoalJpaRepository : JpaRepository<Goal, Long> {
    @Meta(comment = "find by one goal by goal-pk and user-pk")
    fun findByGoalSequenceAndUserSequence(goalSequence: Long, userSequence: Long): Optional<Goal>

    @Meta(comment = "find goals by user")
    fun findAllByUserSequence(userSeq: Long): List<Goal>

    @Meta(comment = "delete one goal by user")
    fun deleteByGoalSequenceAndUserSequence(goalSequence: Long, userSequence: Long): Int
}