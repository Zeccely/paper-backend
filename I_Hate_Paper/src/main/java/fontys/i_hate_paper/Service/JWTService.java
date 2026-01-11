package fontys.i_hate_paper.Service;

import fontys.i_hate_paper.Models.User;
import io.jsonwebtoken.Claims;

public interface JWTService {
    String createAccessToken(User user);
    String createRefreshToken(User user);
    Claims parseClaims (String token);
    Integer getUserIdRefreshToken(String token);

    String extractUsername(String token);
}
