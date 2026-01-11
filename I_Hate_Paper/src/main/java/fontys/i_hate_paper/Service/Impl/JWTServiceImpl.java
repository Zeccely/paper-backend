package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.Configuration.JWTConfigurationProperties;
import fontys.i_hate_paper.Exception.ValidationException;
import fontys.i_hate_paper.Models.User;
import fontys.i_hate_paper.Service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTServiceImpl implements JWTService {

    private final SecretKey key;
    private final Long accessValiditySeconds;
    private final Long refreshValiditySeconds;

    public JWTServiceImpl(JWTConfigurationProperties props) {
        this.key = Keys.hmacShaKeyFor(
                props.getSecretKey().getBytes(StandardCharsets.UTF_8)
        );
        this.accessValiditySeconds = props.getAccessTokenValiditySeconds();
        this.refreshValiditySeconds = props.getRefreshTokenValiditySeconds();
    }

    @Override
    public String createAccessToken(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + accessValiditySeconds * 1000L);

        return Jwts.builder()  //Welcome to the builder pattern. it seems that this way is more readable, prevents confusion of a huge constructor and also errors
                .subject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    @Override
    public String createRefreshToken(User user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + refreshValiditySeconds * 1000L);

        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }


    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public Integer getUserIdRefreshToken(String token) {
        if (token == null || token.isBlank()) {
            throw new ValidationException("REFRESH_TOKEN_MISSING", "Refresh token is missing");
        }

        Claims claims = parseClaims(token);

        String userIdStr = claims.getSubject();
        Integer userId; //I am also primitive.

        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException ex) {
            throw new ValidationException("INVALID_TOKEN", "Invalid user id in token");
        }


        return userId;
    }

    @Override
    public String extractUsername(String token) {
        return "";
    }
}
