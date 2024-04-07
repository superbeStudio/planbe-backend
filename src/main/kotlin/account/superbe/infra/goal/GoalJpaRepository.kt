package account.superbe.infra.goal

import account.superbe.domain.model.goal.Goal
import org.springframework.data.jpa.repository.JpaRepository

interface GoalJpaRepository : JpaRepository<Goal, Long> {
}