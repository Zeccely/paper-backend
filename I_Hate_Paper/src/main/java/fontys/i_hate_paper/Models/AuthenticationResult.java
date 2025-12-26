package fontys.i_hate_paper.Models;

public record AuthenticationResult(
    User user,
    String accessToken,
    String refreshToken
) {}
