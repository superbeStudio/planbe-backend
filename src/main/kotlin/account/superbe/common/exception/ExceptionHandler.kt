package account.superbe.common.exception

import account.superbe.common.io.ResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.RestTemplate

@RestControllerAdvice
class ExceptionHandler {
    @Autowired
    lateinit var env: Environment

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleException(e: Exception): ResponseEntity<ResponseDto<Nothing>> {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto(message = e.message))
    }

    @ExceptionHandler(Exception::class)
    fun handle500Exception(e: Exception): ResponseEntity<ResponseDto<Nothing>> {
        reportErrorToSlack(e.message!!)
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto(message = e.message))
    }

    private val WEBHOOK_URL = "https://hooks.slack.com/services/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"
    private val APP_NAME = "planbe"
    private val ERROR_CHANNEL = "backend-error-log"
    private val ICON_DANGER = ":rotating_light:"
    private fun reportErrorToSlack(message: String) {
        if (listOf(*env.activeProfiles).contains("dev")) {
            try {
                val restTemplate = RestTemplate()
                val parameters: MutableMap<String, Any> = HashMap()
                parameters["icon_emoji"] = ICON_DANGER
                parameters["username"] = APP_NAME
                parameters["text"] = message
                parameters["channel"] = "#$ERROR_CHANNEL"

                val entity = HttpEntity<Map<String, Any>>(parameters)
                restTemplate.exchange<String>(WEBHOOK_URL, HttpMethod.POST, entity, String::class.java)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

    }
}