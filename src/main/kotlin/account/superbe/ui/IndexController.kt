package account.superbe.ui

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class IndexController {
    @GetMapping("/docs")
    fun viewDocs(): String {
        return "/docs/index.html"
    }
}