package account.superbe.domain.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "user_info")
class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "seq", nullable = false)
        val userSequence: Long? = null,
        @Column(name = "email", nullable = false)
        val email: String,
        @Column(name = "nickname", nullable = false)
        val nickname: String,
        @Column(name = "sex", nullable = false)
        @Enumerated(EnumType.STRING)
        val sex: Gen,
        @Column(name = "birth", nullable = false)
        val birth: LocalDate,
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
        @Enumerated(value = EnumType.STRING)
        @Column(name="role") var role: Role,
        @Column(name = "uuid")
        var uuid: UUID? = UUID.randomUUID()
) {
}