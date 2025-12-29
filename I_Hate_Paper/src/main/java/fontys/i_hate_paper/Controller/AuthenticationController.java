package fontys.i_hate_paper.Controller;


import fontys.i_hate_paper.DTOs.CategoryDTO;
import fontys.i_hate_paper.DTOs.UserDTO;
import fontys.i_hate_paper.Models.AuthenticationResult;
import fontys.i_hate_paper.Service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")


public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/testpoint")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @PostMapping("/createuser")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        authenticationService.createUser(userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        AuthenticationResult result = authenticationService.loginWithToken(userDTO.getUsername(), userDTO.getPassword());

        ResponseCookie accessCookie = ResponseCookie.from("ACCESS", result.accessToken())
                .httpOnly(true).secure(false).sameSite("Lax").path("/")
                .maxAge(900)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("REFRESH", result.refreshToken())
                .httpOnly(true).secure(false).sameSite("Lax").path("/")
                .maxAge(432000)
                .build();

        UserDTO out = new UserDTO();
        out.setUsername(result.user().getUsername());
        out.setEmail(result.user().getEmail());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(out);
    }
    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request) {

        String refreshToken = getCookieValue(request, "REFRESH");

        String newAccessToken =
                authenticationService.refreshToken(refreshToken);

        ResponseCookie accessCookie = ResponseCookie.from("ACCESS", newAccessToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(900)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .build();
    }
    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

}
