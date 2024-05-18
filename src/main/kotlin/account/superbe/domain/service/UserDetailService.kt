package account.superbe.domain.service

import account.superbe.infra.UserJpaRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class CustomUserDetailService(
        private val userRepo: UserJpaRepository,
        private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user: account.superbe.domain.model.User = userRepo.findByEmail(username).orElseThrow { throw IllegalArgumentException() }
        val result = UserDetailsImpl(user);
        return result
    }
}