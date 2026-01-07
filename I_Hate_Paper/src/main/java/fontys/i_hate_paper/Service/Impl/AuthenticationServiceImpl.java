package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.DTOs.UserDTO;
import fontys.i_hate_paper.Exception.NotFoundException;
import fontys.i_hate_paper.Exception.ValidationException;
import fontys.i_hate_paper.Models.AuthenticationResult;
import fontys.i_hate_paper.Models.User;
import fontys.i_hate_paper.Repository.UserRepository;
import fontys.i_hate_paper.Service.AuthenticationService;
import fontys.i_hate_paper.Service.JWTService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public String refreshToken(String refreshToken) {
        Integer userId = jwtService.getUserIdRefreshToken(refreshToken);

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("USER_NOT_FOUND", "User was not found");
        }

        User user = optionalUser.get();

        return jwtService.createAccessToken(user);
    }
    public UserDTO AuthUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ValidationException("NOT_AUTHENTICATED", "Not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User user)) {
            throw new ValidationException("INVALID_PRINCIPAL", "Invalid principal");
        }

        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

}
