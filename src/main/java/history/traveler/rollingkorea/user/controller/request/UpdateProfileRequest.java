package history.traveler.rollingkorea.user.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 사용자 프로필의 일부 필드만 업데이트할 때 쓰는 DTO.
 * null인 필드는 응답 JSON에서 제외됩니다.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateProfileRequest(
        @JsonProperty("userName") String userName,
        @JsonProperty("nickName")  String nickname,
        @JsonProperty("location")  String location,
        @JsonProperty("mobile")    String mobile,
        @JsonProperty("birthday")  String birthday
) {}
