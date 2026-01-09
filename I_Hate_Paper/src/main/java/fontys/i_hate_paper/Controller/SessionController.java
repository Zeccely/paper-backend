package fontys.i_hate_paper.Controller;


import fontys.i_hate_paper.DTOs.UserDTO;
import fontys.i_hate_paper.Models.AuthenticationResult;
import fontys.i_hate_paper.Service.AuthenticationService;
import fontys.i_hate_paper.Util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

@RestController
@RequestMapping("/api/sessions")


public class SessionController {
    private final AuthenticationService authenticationService;

    public SessionController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createSession(@RequestBody UserDTO userDTO) {
        AuthenticationResult result = authenticationService.loginWithToken(userDTO.getUsername(), userDTO.getPassword());

        ResponseCookie accessCookie = CookieUtils.buildAccessCookie(result.accessToken());
        ResponseCookie refreshCookie = CookieUtils.buildRefreshCookie(result.refreshToken());

        UserDTO out = new UserDTO();
        out.setUsername(result.user().getUsername());
        out.setEmail(result.user().getEmail());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(out);
    }
    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshSession(HttpServletRequest request) {

        String refreshToken = CookieUtils.getCookieValue(request, "REFRESH");

        String newAccessToken =
                authenticationService.refreshToken(refreshToken);

        ResponseCookie accessCookie = CookieUtils.buildAccessCookie(newAccessToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSession() {

        ResponseCookie clearAccess = CookieUtils.clearAccessCookie();
        ResponseCookie clearRefresh = CookieUtils.clearRefreshCookie();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, clearAccess.toString())
                .header(HttpHeaders.SET_COOKIE, clearRefresh.toString())
                .build();
    }

}



