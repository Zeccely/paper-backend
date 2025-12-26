package fontys.i_hate_paper.Filter;

import fontys.i_hate_paper.Exception.NotFoundException;
import fontys.i_hate_paper.Exception.ValidationException;
import fontys.i_hate_paper.Models.User;
import fontys.i_hate_paper.Repository.UserRepository;
import fontys.i_hate_paper.Service.JWTService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    public JWTFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getCookieValue(request);
        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = jwtService.validateToken(token);

            String userIdStr = claims.getSubject();
            Integer userId = Integer.parseInt(userIdStr);

            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isEmpty()) {
                throw new NotFoundException("USER_NOT_FOUND", "User was not found");
            }

            User user = optionalUser.get();

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception ex) {

            SecurityContextHolder.clearContext();
            throw new ValidationException("TOKEN_IS_INVALID", "This token is invalid");

        }

        filterChain.doFilter(request, response);
    }

    private String getCookieValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
        {
            throw new NotFoundException("COOKIE_NOT_FOUND", "Cookie not found");
        }

        for (Cookie cookie : cookies) {
            if ("ACCESS".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
