package fontys.i_hate_paper.Service;

import fontys.i_hate_paper.DTOs.UserDTO;
import fontys.i_hate_paper.Models.AuthenticationResult;
import fontys.i_hate_paper.Models.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    User createUser(UserDTO userDTO);
    User getUserByUsername(String username);
    AuthenticationResult loginWithToken(String username, String password);
    String refreshToken(String refreshToken);
    UserDTO AuthUser(Authentication authentication);
}
