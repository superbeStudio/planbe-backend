package account.superbe.application

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RootController {
    @GetMapping("/api/test")
    fun serverConnectionCheck(): String {
        return "ok";
    }
}