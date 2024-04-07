package account.superbe.domain.model.goal

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "goal_info")
class Goal (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "uuid")
        val uuid: Long? = null,
        @Column(name = "goal_name")
        var goalName: String,
        @Column(name = "goal_category")
        var goalCategory: String,
        @Column(name = "goal_amount")
        var goalAmount: Int,
        @Column(name = "priority")
        var priority: Int,
        @Column(name = "goal_time")
        var goalTime: String,
        @Column(name = "goal_url")
        var goalUrl: String,
        @Column(name = "create_datetime")
        val createDatetime: LocalDateTime,
        @Column(name = "update_datetime")
        var updateDatetime: LocalDateTime,
){
}