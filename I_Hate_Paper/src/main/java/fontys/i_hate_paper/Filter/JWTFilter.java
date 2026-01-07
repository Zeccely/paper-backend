package fontys.i_hate_paper.Filter;

import fontys.i_hate_paper.Models.AuthPrincipal;
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
import java.util.Collections;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
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

            String username = claims.get("username", String.class);

            AuthPrincipal principal = new AuthPrincipal(userId, username);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);


            //sqp_c2b033661ee052d9f11e0569d38d5f969fd8fdf5

        } catch (Exception ex) {

            SecurityContextHolder.clearContext();

        }

        filterChain.doFilter(request, response);
    }

    private String getCookieValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
        {
            return null;
        }

        for (Cookie cookie : cookies) {
            if ("ACCESS".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
