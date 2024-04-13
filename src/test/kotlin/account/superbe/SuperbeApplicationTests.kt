package account.superbe

import account.superbe.domain.model.Gen
import account.superbe.domain.model.User
import account.superbe.infra.ExpenseJpaRepository
import account.superbe.infra.GoalJpaRepository
import account.superbe.infra.UserJpaRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
class SuperbeApplicationTests (
        @Autowired val userRepo: UserJpaRepository,
        @Autowired val goalRepo: GoalJpaRepository,
        @Autowired val expenseRepo: ExpenseJpaRepository
){

    @Test
    fun dbConnectionTest() {
        println(userRepo.findAll().count())
        println(goalRepo.findAll().count())
        println(expenseRepo.findAll().count())
    }

    @Test
//    @Transactional
    fun userInitTest() {
        val user = User(
                email = "test@email.com",
                nickname = "test",
                sex = Gen.F,
                age = 20,
                password = "test",
                currencyMain = "won",
                modeScreen = "D",
                createDate = LocalDateTime.now(),
                updateDate = LocalDateTime.now())
        userRepo.save(user)
        val findUser = userRepo.findByIdOrNull(user.uuid) ?: throw RuntimeException("회원없음")
        assertThat(user.uuid).isEqualTo(findUser.uuid)
    }

}
