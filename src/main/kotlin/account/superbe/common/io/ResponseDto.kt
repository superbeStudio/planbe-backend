package account.superbe.common.io

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data
import lombok.Getter
import java.time.LocalDateTime

@Data
class ResponseDto<T> (
        val message: String? = null,
        @JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
        val time: LocalDateTime = LocalDateTime.now(),
        var data: T? = null
){
}