package account.superbe.common.io

import lombok.Data
import lombok.Getter
import java.time.LocalDateTime

@Data
class ResponseDto<T> (
        val message: String? = null,
        val time: LocalDateTime = LocalDateTime.now(),
        val data: T? = null
){
}