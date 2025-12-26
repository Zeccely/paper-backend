package fontys.i_hate_paper.Controller;


import fontys.i_hate_paper.DTOs.UserDTO;
import fontys.i_hate_paper.Models.AuthenticationResult;
import fontys.i_hate_paper.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")


public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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
}
