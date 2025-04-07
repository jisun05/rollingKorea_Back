package history.traveler.rollingkorea.user.controller.request;

import history.traveler.rollingkorea.user.domain.LoginType;
import jakarta.validation.constraints.NotNull;


public record GoogleLoginRequest(String idToken) {}