package account.superbe.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.time.Duration

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                authorizeRequests ->
                    authorizeRequests
                        .requestMatchers("/**").permitAll()
            }
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
        http
                .headers{headerConfig ->
                    headerConfig.frameOptions{frameOptions -> frameOptions.disable()}
                }
                .cors { corsConfigurationSource: CorsConfigurer<HttpSecurity?> -> corsConfigurationSource.configurationSource(corsConfigurationSource()) }

        return http.build()
    }

    fun corsConfigurationSource(): CorsConfigurationSource {
        val urlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()

        val corsConfiguration = CorsConfiguration()
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL)
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL)

        corsConfiguration.setAllowedOrigins(listOf("/**"))
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration)

        corsConfiguration.allowCredentials = true
        corsConfiguration.setMaxAge(Duration.ofHours(2))

        return urlBasedCorsConfigurationSource
    }

}