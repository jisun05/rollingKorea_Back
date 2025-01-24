package history.traveler.rollingkorea.global.config.secutiry;

public interface OAuth2UserInfo {

    String getProvider();
    String getProviderId();
    String getName();
    String getEmail();
}
