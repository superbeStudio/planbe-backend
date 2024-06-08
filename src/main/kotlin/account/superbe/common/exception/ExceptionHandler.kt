package account.superbe.common.exception

import account.superbe.common.io.ResponseDto
import io.sentry.Sentry
import jakarta.validation.ConstraintViolationException
import org.springframework.beans.TypeMismatchException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.json.JsonParseException
import org.springframework.core.env.Environment
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.RestTemplate
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.MultipartException
import org.springframework.web.multipart.support.MissingServletRequestPartException

@RestControllerAdvice
class ExceptionHandler {
    @Autowired
    lateinit var env: Environment

    @ExceptionHandler(IllegalArgumentException::class,
            MethodArgumentNotValidException::class,
            JsonParseException::class,
            HttpMessageNotReadableException::class,
            HttpMediaTypeNotSupportedException::class,
            HttpMessageNotReadableException::class,
            MissingServletRequestPartException::class,
            MissingServletRequestParameterException::class,
            ConstraintViolationException::class,
            TypeMismatchException::class,
            MethodArgumentTypeMismatchException::class,
            MultipartException::class,
            UnsatisfiedServletRequestParameterException::class,
            HttpRequestMethodNotSupportedException::class
    )
    fun handleException(e: Exception): ResponseEntity<ResponseDto<Nothing>> {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto(message = e.message))
    }

    @ExceptionHandler(Throwable::class, DataIntegrityViolationException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handle500Exception(e: Exception): ResponseEntity<ResponseDto<Nothing>> {
        Sentry.captureException(e); //sentry 에러 전송
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto(message = e.message))
    }

    private val WEBHOOK_URL = "https://hooks.slack.com/services/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"
    private val APP_NAME = "planbe"
    private val ERROR_CHANNEL = "backend-error-log"
    private val ICON_DANGER = ":rotating_light:"
    private fun reportErrorToSlack(message: String?) {
        if (listOf(*env.activeProfiles).contains("dev")) {
            try {
                val restTemplate = RestTemplate()
                val parameters: MutableMap<String, Any> = HashMap()
                parameters["icon_emoji"] = ICON_DANGER
                parameters["username"] = APP_NAME
                parameters["text"] = message ?: "NO MESSAGE"
                parameters["channel"] = "#$ERROR_CHANNEL"

                val entity = HttpEntity<Map<String, Any>>(parameters)
                restTemplate.exchange(WEBHOOK_URL, HttpMethod.POST, entity, String::class.java)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

    }
}