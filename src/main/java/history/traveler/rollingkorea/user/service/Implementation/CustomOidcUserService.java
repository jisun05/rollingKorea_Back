package history.traveler.rollingkorea.user.service.Implementation;

import history.traveler.rollingkorea.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Component;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Component
@RequiredArgsConstructor
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final UserService userService; // 기존 UserServiceImpl 재활용

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = new OidcUserService().loadUser(userRequest);
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        userService.findOrCreateGoogleUser(email, name);
        return oidcUser;
    }
}
