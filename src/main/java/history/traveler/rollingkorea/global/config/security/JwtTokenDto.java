package history.traveler.rollingkorea.global.config.security;

public record JwtTokenDto(
        String grantType, String accessToken, Long accessTokenExpiresIn
) {

}
