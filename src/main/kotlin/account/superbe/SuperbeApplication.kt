package account.superbe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SuperbeApplication

fun main(args: Array<String>) {
    runApplication<SuperbeApplication>(*args)


}
