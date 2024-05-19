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

@OpenAPIDefinition(
        info = Info(title = "PLANBE API 명세서",
                version = "v0.0.1",
                contact = Contact(
                        name = "superbe",
                        email = "superbestudio@gmail.com"
                )
        ),
        servers = [Server(url = "https://planbe-backend.p-e.kr/", description = "dev 서버")]
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@SpringBootApplication
class SuperbeApplication

fun main(args: Array<String>) {
    runApplication<SuperbeApplication>(*args)


}
