package account.superbe.infra

import account.superbe.domain.model.Goal
import org.springframework.data.jpa.repository.JpaRepository

interface GoalJpaRepository : JpaRepository<Goal, Long> {
}