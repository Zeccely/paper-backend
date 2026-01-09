package fontys.i_hate_paper.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
public class AuthPrincipal {
    private Integer userId;
    private String userName;
}
