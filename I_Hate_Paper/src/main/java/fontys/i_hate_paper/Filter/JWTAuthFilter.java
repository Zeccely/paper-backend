package fontys.i_hate_paper.Filter;

import fontys.i_hate_paper.Service.JWTService;
import fontys.i_hate_paper.Util.CookieUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1) Read access token from cookie
        String token = CookieUtils.getCookieValue(request, "ACCESS");

        // No token -> just continue
        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2) Only set auth if not already set
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth == null || !existingAuth.isAuthenticated()) {

            // 3) Parse/validate token (your method returns Claims or throws)
            Claims claims;
            try {
                claims = jwtService.parseClaims(token);
            } catch (Exception e) {
                // Invalid/expired token -> ignore, continue without auth
                filterChain.doFilter(request, response);
                return;
            }

            // 4) Extract username/email from token subject
            String username = claims.getSubject(); // subject = username/email

            if (username != null && !username.isBlank()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 5) Continue chain
        filterChain.doFilter(request, response);
    }
}
