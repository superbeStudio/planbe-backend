package account.superbe.security

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class TokenDto(val grantType: String? = "Bearer",
              val refreshToken: String,
              val accessToken: String? = null
) {
}