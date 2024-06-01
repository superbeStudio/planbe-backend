package account.superbe.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "category_info")
class Category (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "cate_seq") var categorySequence: Long? = null,
        @Column(name = "cate_name") var categoryName: String,
        @Column(name = "cate_type")  @Enumerated(EnumType.STRING) var categoryType: CategoryType,
        @Column(name = "user_seq") var userSequence: Long,
        @Column(name = "create_datetime") val createDatetime: LocalDateTime = LocalDateTime.now(),
        @Column(name = "update_datetime") var updateDatetime: LocalDateTime = LocalDateTime.now(),
){
        fun updateData(categoryType: CategoryType?, categoryName: String?) {
                categoryType?.also { this.categoryType = it }
                categoryName?.also { this.categoryName = it }
                updateDatetime = LocalDateTime.now()
        }
}