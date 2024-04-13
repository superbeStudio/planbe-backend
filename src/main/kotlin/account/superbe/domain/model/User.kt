package account.superbe.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_info")
class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "uuid", nullable = false)
        val uuid: Long? = null,
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
        var updateDate: LocalDateTime = LocalDateTime.now()
) {
}