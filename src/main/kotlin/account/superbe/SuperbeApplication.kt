package account.superbe

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
        info = Info(title = "PLANBE API 명세서",
                version = "v0.0.1",
                contact = Contact(
                        name = "superbe",
                        email = "superbestudio@gmail.com"
                )
        )
)
@SecurityScheme(type = SecuritySchemeType.APIKEY, `in` = SecuritySchemeIn.HEADER, name = "Api-Token", description = "Auth Token")
@SpringBootApplication
class SuperbeApplication

fun main(args: Array<String>) {
    runApplication<SuperbeApplication>(*args)


}
