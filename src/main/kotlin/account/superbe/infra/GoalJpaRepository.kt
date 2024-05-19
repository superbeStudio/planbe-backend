package account.superbe.infra

import account.superbe.domain.model.Goal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

interface GoalJpaRepository : JpaRepository<Goal, Long> {
    fun findByGoalSequenceAndUserSequence(goalSequence: Long, userSequence: Long): Optional<Goal>
    fun findAllByUserSequence(userSeq: Long): List<Goal>

    @Modifying
    @Query("""
        delete from Goal 
        where goalSequence = :goalSequence and userSequence = :userSequence
    """)
    fun deleteByGoalSequenceAndUserSequence(goalSequence: Long, userSequence: Long): Int
}