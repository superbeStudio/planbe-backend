package account.superbe.infra

import account.superbe.domain.model.Goal
import org.hibernate.annotations.NamedQuery
import org.springframework.data.annotation.QueryAnnotation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Meta
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

interface GoalJpaRepository : JpaRepository<Goal, Long> {
    @Meta(comment = "find by one goal by goal-pk and user-pk")
    fun findByGoalSequenceAndUserSequence(goalSequence: Long, userSequence: Long): Optional<Goal>
    @Meta(comment = "find goals by user")
    fun findAllByUserSequence(userSeq: Long): List<Goal>

    @Meta(comment = "delete one goal by user")
    @Modifying
    @Query("""
        delete from Goal 
        where goalSequence = :goalSequence and userSequence = :userSequence
    """)
    fun deleteByGoalSequenceAndUserSequence(goalSequence: Long, userSequence: Long): Int
}