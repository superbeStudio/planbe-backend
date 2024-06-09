package account.superbe.domain.service.user

import account.superbe.application.dto.UserDto
import account.superbe.domain.model.Gen
import account.superbe.domain.model.User
import account.superbe.infra.UserFactory
import account.superbe.infra.UserJpaRepository
import account.superbe.security.JwtTokenProvider
import account.superbe.security.TokenDto
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.util.*


@Service
@RequiredArgsConstructor
class UserService(private val userRepo: UserJpaRepository, private val userFactory: UserFactory, private val jwtTokenProvider: JwtTokenProvider,
                  private val authenticationManagerBuilder: AuthenticationManagerBuilder) {
    val log: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun getUserByEmailNonNull(email: String): UserDto {
        val user = userRepo.findByEmail(email).orElseThrow { throw IllegalArgumentException("로그인 정보를 다시 확인해주세요") }
        log.info("[getUserByEmailNonNull] email = {}", email)
        return UserDto(email = user.email, password = user.password, nickname = user.nickname, sex = user.sex,
                birth = user.birth, userSeq = user.userSequence)
    }

    @Throws(JsonProcessingException::class)
    fun kakaoLogin(code: String, response: HttpServletResponse): Long {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        val accessToken = getAccessToken(code)

        // 2. 토큰으로 카카오 API 호출
        val kakaoUserInfo = getKakaoUserInfo(accessToken)

        // 3. 카카오ID로 회원가입 처리
        val kakaoUser = registerKakaoUserIfNeed(kakaoUserInfo)

        // 4. 강제 로그인 처리
        var authentication: Authentication = forceLogin(kakaoUser)

        val authenticationToken =
                UsernamePasswordAuthenticationToken(kakaoUser.email, kakaoUser.password)

        authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken)

        val refreshToken: String = jwtTokenProvider.generateToken(authentication).refreshToken

//        redisService.setValues(refreshToken, kakaoUser.email())

//        kakaoUserInfo.setToken(jwtTokenProvider.generateToken(authentication))

        return kakaoUserInfo.userSeq!!
    }

    @Throws(JsonProcessingException::class)
    private fun getAccessToken(code: String): String {
        // HTTP Header 생성
        val headers: HttpHeaders = HttpHeaders()
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        // HTTP Body 생성
        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("grant_type", "authorization_code")
        body.add("client_id", "4c2ba3938a53b9849aef10abf1a2283e")
        body.add("redirect_uri", "http://watermelonfront.s3-website.ap-northeast-2.amazonaws.com/kakao")
        body.add("code", code)

        // HTTP 요청 보내기
        val kakaoTokenRequest = HttpEntity(body, headers)
        val rt = RestTemplate()
        val response = rt.exchange<String>(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String::class.java
        )

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        val responseBody = response.body
        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(responseBody)
        return jsonNode["access_token"].asText()
    }

    @Throws(JsonProcessingException::class)
    private fun getKakaoUserInfo(accessToken: String): UserDto {
        // HTTP Header 생성
        val headers: HttpHeaders = HttpHeaders()
        headers.add("Authorization", "Bearer $accessToken")
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        // HTTP 요청 보내기
        val kakaoUserInfoRequest = HttpEntity<MultiValueMap<String, String>>(headers)
        val rt = RestTemplate()
        val response = rt.exchange<String>(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String::class.java
        )

        // responseBody에 있는 정보를 꺼냄
        val responseBody = response.body
        val objectMapper = ObjectMapper()
        val jsonNode = objectMapper.readTree(responseBody)
        println(jsonNode)

        val seq = jsonNode["id"].asLong()
        val id = jsonNode["id"].asText()
        val nickname = jsonNode["properties"]["nickname"].asText()
        val sex = Gen.valueOf(jsonNode["sex"].asText())
        val date = jsonNode["birth"].asText()
        val birth: LocalDate = LocalDate.parse(date)

        return UserDto(userSeq = seq, nickname = nickname, email = id, sex = sex, birth = birth)
    }

    private fun registerKakaoUserIfNeed(kakaoUserInfo: UserDto): User {
        // DB 에 중복된 email이 있는지 확인
        val email: String = kakaoUserInfo.email
        val nickname: String = kakaoUserInfo.nickname
        var kakaoUser = userRepo.findByEmail(email).orElse(null)

        if (kakaoUser == null) {
            kakaoUser = userFactory.create(kakaoUserInfo)

            userRepo.save(kakaoUser)
        }
        return kakaoUser
    }

    private fun forceLogin(kakaoUser: User): Authentication {
        val userDetails: UserDetails = UserDetailsImpl(kakaoUser)
        val authentication: Authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        SecurityContextHolder.getContext().authentication = authentication
        return authentication
    }

    // 5. response Header에 JWT 토큰 추가
    private fun kakaoUsersAuthorizationInput(authentication: Authentication, response: HttpServletResponse) {
        // response header에 token 추가
        val token: TokenDto = jwtTokenProvider.generateToken(authentication)
        response.addHeader("Authorization", "BEARER $token")
    }
}