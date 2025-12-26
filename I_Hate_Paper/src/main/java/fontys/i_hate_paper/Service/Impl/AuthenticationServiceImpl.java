package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.DTOs.UserDTO;
import fontys.i_hate_paper.Exception.ValidationException;
import fontys.i_hate_paper.Models.AuthenticationResult;
import fontys.i_hate_paper.Models.User;
import fontys.i_hate_paper.Repository.UserRepository;
import fontys.i_hate_paper.Service.AuthenticationService;
import fontys.i_hate_paper.Service.JWTService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    public AuthenticationServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, JWTService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new ValidationException("USER_IS_NULL", "There is no user");
        }

        User incomingUser = new User();
        incomingUser.setUsername(userDTO.getUsername());
        incomingUser.setEmail(userDTO.getEmail());
        incomingUser.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.save(incomingUser);

    }

    @Override
    public User getUserbyUsername(String username) {
        if (username == null) {
            throw new ValidationException("USER_IS_NULL", "There is no username");
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public AuthenticationResult loginWithToken(String username, String password) {
        if (username == null || password == null) {
            throw new ValidationException("DATA_IS_NULL", "One of the fields is null");
        }
        User user = getUserbyUsername(username);

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new ValidationException("INVALID_CREDENTIALS", "Password is incorrect");
        }
        String accessToken = jwtService.createAccessToken(user);
        String refreshToken = jwtService.createRefreshToken(user);

        return new AuthenticationResult(user, accessToken, refreshToken);

    }
}
