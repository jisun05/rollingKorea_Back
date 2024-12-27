package history.traveler.springbootdeveloper.domain;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getName();
    String getEmail();
}
