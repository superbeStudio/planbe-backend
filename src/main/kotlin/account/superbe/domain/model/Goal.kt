package account.superbe.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "goal_info")
class Goal (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "seq")
        val seq: Long? = null,
        @Column(name = "goal_name")
        var goalName: String,
        @Column(name = "goal_category")
        var goalCategory: String,
        @Column(name = "goal_amount")
        var goalAmount: Int,
        @Column(name = "priority")
        var priority: Int,
        @Column(name = "goal_time")
        var goalTime: LocalDate,
        @Column(name = "goal_url")
        var goalUrl: String,
        @Column(name = "create_datetime")
        val createDatetime: LocalDateTime = LocalDateTime.now(),
        @Column(name = "update_datetime")
        var updateDatetime: LocalDateTime = LocalDateTime.now(),
        @Column(name = "uuid")
        val uuid: UUID? = UUID.randomUUID()
){
}