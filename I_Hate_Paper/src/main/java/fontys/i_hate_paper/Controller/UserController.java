package fontys.i_hate_paper.Controller;

import fontys.i_hate_paper.DTOs.UserDTO;
import fontys.i_hate_paper.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {
//due to lack of admin features, endpoints on Get for get users and getusers by id won't exist...
//This is vulnerable to broken access control.
    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        System.out.println("Ouch! said createuser");
        authenticationService.createUser(userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(Authentication authentication) {
        return ResponseEntity.ok(authenticationService.AuthUser(authentication));
    }
}
