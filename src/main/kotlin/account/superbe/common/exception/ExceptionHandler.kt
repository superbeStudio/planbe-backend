package account.superbe.common.exception

import account.superbe.common.io.ResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBizException(e: Exception): ResponseEntity<ResponseDto<Nothing>> {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto(message = e.message))
    }
}