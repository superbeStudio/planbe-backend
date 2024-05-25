import account.superbe.SuperbeApplication
import account.superbe.infra.UserJpaRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [SuperbeApplication::class])
class UserTest(@Autowired var userJpaRepository: UserJpaRepository){
    @Test
    fun test() {
        userJpaRepository.findByEmail("string")
    }
}