package tj.alimov.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationResponseDto {
    String access_token;
    String refresh_token;
}
