
package Quick.service.Authentication;

import org.springframework.http.ResponseEntity;
import Quick.dto.SignInDto;
import Quick.dto.SignupRequestDto;
import Quick.dto.TokenResponseDto;
public interface AuthService {

    void userSignUp(SignupRequestDto signUpRequest);

    ResponseEntity<?> userSignIn(SignInDto request);

    ResponseEntity<TokenResponseDto> refresh(String refreshToken);

    void logout(String refreshToken);
}