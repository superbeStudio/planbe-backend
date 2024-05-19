package account.superbe.infra

import account.superbe.domain.model.Goal
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface GoalJpaRepository : JpaRepository<Goal, Long> {
    fun findByGoalSequenceAndUserSequence(goalSequence: Long, userSequence: Long): Optional<Goal>
    fun findAllByUserSequence(userSeq: Long): List<Goal>
}