package account.superbe.ui

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RootController {
    @GetMapping
    fun serverConnectionCheck(): String {
        return "ok";
    }
}