package account.superbe.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.util.StringUtils
import org.springframework.util.StringUtils.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Objects
import java.util.UUID

@Entity
@Table(name = "goal_info")
class Goal (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "goal_seq")
        val goalSequence: Long? = null,
        @Column(name = "goal_name")
        var goalName: String,
        @Column(name = "cate_seq")
        var categorySequence: Long,
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
        val uuid: UUID? = UUID.randomUUID(),
        @Column(name = "user_seq")
        val userSequence: Long
){
        fun updateData(
                goalName: String?, categorySequence: Long?, goalAmount: Int?, priority: Int?, goalTime: LocalDate?,
                goalUrl: String?
        ) {
                if(hasText(goalName)) {
                        this.goalName = goalName!!
                }
                if(categorySequence != null) {
                        this.categorySequence = categorySequence!!
                }
                if(hasText(goalUrl)) {
                        this.goalUrl = goalUrl!!
                }
                goalAmount?.also { this.goalAmount = it }
                priority?.also { this.priority = it }
                goalTime?.also { this.goalTime = it }
                this.updateDatetime = LocalDateTime.now()
        }
}