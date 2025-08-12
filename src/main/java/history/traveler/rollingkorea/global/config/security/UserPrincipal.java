package history.traveler.rollingkorea.user.security;

import history.traveler.rollingkorea.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

/**
 * 스프링 시큐리티가 Authentication 객체에 담아주는 사용자 정보(UserDetails + OAuth2User).
 */
public class UserPrincipal implements OAuth2User, UserDetails {

    private final Long userId;
    private final String loginId;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    private UserPrincipal(Long userId, String loginId, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.loginId = loginId;
        this.authorities = authorities;
    }

    /**
     * 도메인 User → UserPrincipal 변환 (일반 로그인용)
     */
    public static UserPrincipal create(User user) {
        List<GrantedAuthority> auths = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserPrincipal(user.getUserId(), user.getLoginId(), auths);
    }

    /**
     * OAuth2 로그인 시 호출 (attributes 포함)
     */
    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal principal = create(user);
        principal.setAttributes(attributes);
        return principal;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String getName() {
        // OAuth2User.getName()
        return String.valueOf(userId);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    private void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    // --- UserDetails ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        // OAuth2 로그인만 사용한다면 패스워드는 관리하지 않음
        return null;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
