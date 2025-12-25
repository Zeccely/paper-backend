package fontys.i_hate_paper.Service;

import fontys.i_hate_paper.DTOs.UserDTO;
import fontys.i_hate_paper.Models.User;

public interface AuthenticationService {
    User createUser(UserDTO userDTO);
    User getUserbyUsername(String username);
    User login(String username, String password);
}
