package fontys.i_hate_paper.Util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public class CookieUtils {
    private CookieUtils() {
        // prevent instantiation
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public static ResponseCookie buildAccessCookie(String token) {
        return ResponseCookie.from("ACCESS", token)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(900)
                .build();
    }

    public static ResponseCookie buildRefreshCookie(String token) {
        return ResponseCookie.from("REFRESH", token)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(432000)
                .build();
    }

    public static ResponseCookie clearAccessCookie() {
        return ResponseCookie.from("ACCESS", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
    }

    public static ResponseCookie clearRefreshCookie() {
        return ResponseCookie.from("REFRESH", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
    }
}

//I ended up making the cookie "helper"
