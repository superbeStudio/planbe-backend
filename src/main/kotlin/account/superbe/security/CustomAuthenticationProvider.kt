package account.superbe.security

import account.superbe.domain.service.user.CustomUserDetailService
import lombok.RequiredArgsConstructor
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.naming.AuthenticationException


@Component
@RequiredArgsConstructor
class CustomAuthenticationProvider(
        private val userDetailsService: CustomUserDetailService,
        private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {


    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val user: UserDetails = userDetailsService.loadUserByUsername(authentication.name)

        val decodePassword: String = authentication.credentials.toString()
        if (passwordEncoder.matches(decodePassword, user.password)) {
            return UsernamePasswordAuthenticationToken(user.username, user.password, user.authorities)
        }
        throw IllegalArgumentException("사용자 인증 실패")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken::class.java)
    }
}