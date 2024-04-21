//package account.superbe.common.client
//
//import lombok.RequiredArgsConstructor
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.data.redis.core.ValueOperations
//import org.springframework.stereotype.Component
//import java.time.Duration
//
////@Component
//class RedisClient (val redisTemplate: RedisTemplate<String, String>){
//
//    fun setValues(token: String, id: String) {
//        val values: ValueOperations<String, String> = redisTemplate.opsForValue()
//        values.set(token, id, Duration.ofMinutes(60 * 24 * 24)) //3분뒤 메모리에서 삭제
//    }
//
//    fun getValues(token: String): String? {
//        val values: ValueOperations<String, String> = redisTemplate.opsForValue()
//        return values[token]
//    }
//
//    fun delValues(token: String) {
//        redisTemplate.delete(token)
//    }
//}