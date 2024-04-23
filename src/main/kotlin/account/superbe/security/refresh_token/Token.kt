package account.superbe.security.refresh_token

import jakarta.persistence.*

//redis 서버 개설 전 임시 db사용
@Entity
@Table(name = "token")
class Token(
        @Id
        @Column(name = "token")
        val refreshToken: String,
        @Column(name = "email")
        val email: String
) {
}