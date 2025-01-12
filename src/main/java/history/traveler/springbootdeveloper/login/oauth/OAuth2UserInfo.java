package history.traveler.springbootdeveloper.login.oauth;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getName();
    String getEmail();
}
