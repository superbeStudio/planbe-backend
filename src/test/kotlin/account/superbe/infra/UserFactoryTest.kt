package account.superbe.infra

import account.superbe.application.dto.UserDto
import account.superbe.domain.model.Gen
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@SpringBootTest
@Transactional
class UserFactoryTest @Autowired constructor(var userFactory: UserFactory, val passwordEncoder: PasswordEncoder, val userRepo: UserJpaRepository) {
    private var email = "test1@test.com"

    @BeforeEach
    fun setUp() {
        val create = userFactory.create(
                UserDto(email = email, password = "test", nickname = "test", sex = Gen.F, birth = LocalDate.now().minusYears(20)))

        val save = userRepo.save(create)
    }

    @Test
    @DisplayName("유저 객체 생성 성공")
    fun create_success() {
        val password = "test"
        val create = userFactory.create(
                UserDto(email = "test2@test.com", password = password, nickname = "test", sex = Gen.F, birth = LocalDate.now().minusYears(20)))

        //비밀번호 복호화 성공
        assertThat(passwordEncoder.matches(password, create.password)).isTrue()
        val save = userRepo.save(create)
        assertThat(save).isNotNull()
    }

    @Test
    @DisplayName("유저 객체 생성 실패 - 중복 이메일")
    fun create_fail_duplicate_email() {
        assertThatThrownBy {
            assertThat(userFactory.create(
                    UserDto(email = email, password = "test", nickname = "test", sex = Gen.F, birth = LocalDate.now().minusYears(20))))
        }
    }


}