package fontys.i_hate_paper.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
public class AuthPrincipal {
    private Integer userId;
    private String userName;

    //this is because I am being picky and I don't want to add User imports on controller
    //to get the information of the user so... this is nice
}
