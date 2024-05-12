package account.superbe.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "user_info")
class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "uuid", nullable = false)
        val seq: Long? = null,
        @Column(name = "email", nullable = false)
        val email: String,
        @Column(name = "nickname", nullable = false)
        val nickname: String,
        @Column(name = "sex", nullable = false)
        @Enumerated(EnumType.STRING)
        val sex: Gen,
        @Column(name = "age", nullable = false)
        val age: Int,
        @Column(name = "pw")
        var password: String,
        @Column(name = "currency_main")
        var currencyMain: String? = null,
        @Column(name = "mode_screen")
        var modeScreen: String? = null,
        @Column(name = "create_date")
        val createDate: LocalDateTime = LocalDateTime.now(),
        @Column(name = "update_date")
        var updateDate: LocalDateTime = LocalDateTime.now(),
        @Enumerated(value = EnumType.STRING) //설정안하면 enum이 저장된 위치로 DB에 저장됨. enum순서가 틀어지면 DB가 꼬일 가능성이 있어서 순서로 저장하는것은 권장하는 방법이 아님
        @Column(name="mi_role") var role: Role,
        @Column(name = "uuid")
        var uuid: UUID? = UUID.randomUUID()
) {
}