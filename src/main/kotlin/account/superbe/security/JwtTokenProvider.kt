package account.superbe.security

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders.BASE64
import io.jsonwebtoken.security.Keys
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Component
@RequiredArgsConstructor
class JwtTokenProvider(
        @Value("\${jwt.secretKey}") secretKey: String,
        private val authBuilder: AuthenticationManagerBuilder
) {
    val log: Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)
    private lateinit var key: Key
    private val tokenExpireMinutes = 60 //토근 만료시간 (현재 일주일)
    private val refreshExpireMinutes = 60 * 24 * 24 //리프레쉬 토큰 만료시간(현재 한달) 자동로그인도 풀리는경우

    init {
        val keyBytes: ByteArray = BASE64.decode(secretKey)
        this.key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(authentication: Authentication): TokenDto {
        val authorities = authentication.authorities.stream().map { obj: GrantedAuthority -> obj.authority }.collect(Collectors.joining(","))
        val expires = Date(Date().time + tokenExpireMinutes * 60 * 1000)
        val refreshExpires = Date(Date().time + refreshExpireMinutes * 60 * 1000)
        val accessToken: String = Jwts.builder().setSubject(authentication.name).claim("auth", authorities).setExpiration(expires)
                .signWith(key, SignatureAlgorithm.HS256).compact()
        val refreshToken: String = Jwts.builder().setExpiration(refreshExpires)
                .signWith(key, SignatureAlgorithm.HS256).compact()
        return TokenDto(accessToken = accessToken, refreshToken = refreshToken)
    }

    fun getAuthentication(accessToken: String?): Authentication {
        val claims: Claims = parseClaims(accessToken)
        if (claims.get("auth") == null) {
            throw IllegalArgumentException("권한 정보가 없는 토큰입니다.")
        }
        val authorities: Collection<GrantedAuthority> =
                claims["auth"].toString().split(",")
                        .map { role -> SimpleGrantedAuthority(role) }
                        .toList()


        val principal: UserDetails = User(claims.getSubject(), "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun parseClaims(accessToken: String?): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody()
        } catch (e: ExpiredJwtException) {
            e.getClaims()
        }
    }

    fun validateToken(token: String): Boolean { //토큰이 유효한지 검사
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            log.info("Invalid JWT Token$e") //등록안됨
        } catch (e: MalformedJwtException) {
            log.info("Invalid JWT Token$e")
        } catch (e: ExpiredJwtException) {
            log.info("Expired JWT Token$e")
        } catch (e: UnsupportedJwtException) {
            log.info("Unsupported JWT Token$e") //토큰형태가 아님
        } catch (e: IllegalArgumentException) {
            log.info("JWT claims string is empty.$e") //없는 토큰임
        } catch (e: Exception) {
            log.info("JWT token Error.$e")
        }
        return false
    }

    fun isRefreshTokenExpired(refreshToken: String): Boolean {
        val claims: Claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken).getBody()

        val expiration: Date = claims.expiration

        return expiration.before(Date())
    }

    fun getAccessToken(email: String, password: String): TokenDto {
        val authenticationToken =
                UsernamePasswordAuthenticationToken(email, password)

        val authentication: Authentication =
                authBuilder.getObject().authenticate(authenticationToken)

        return generateToken(authentication)
    }

}