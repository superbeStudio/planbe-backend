package account.superbe.domain.model.user

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity
@Table(name = "user_info")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid")
    val uuid: Long,
    @Column(name = "email")
    val email: String,
    @Column(name = "nickname")
    val nickname: String,
    @Column(name = "sex")
    val sex: String,
    @Column(name = "age")
    val age: String,
    @Column(name = "pw")
    val password: String,
    @Column(name = "currency_main")
    val currencyMain: String,
    @Column(name = "mode_screen")
    val modeScreen: String,
    @Column(name = "create_date")
    val createDate: LocalDateTime,
    @Column(name = "update_date")
    var updateDate: LocalDateTime
) {
}