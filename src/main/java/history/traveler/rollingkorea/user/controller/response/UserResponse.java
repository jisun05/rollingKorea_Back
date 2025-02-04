package history.traveler.rollingkorea.user.controller.response;

import history.traveler.rollingkorea.user.domain.User;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {


    private String loginId;
    private String userName;            //real name
    private String nickname;
    private String birthday;
    private String gender;
    private String location;         //nationality
    private String phoneNumber;           //mobile number
    private List<String> roles;

    // User -> UserResponse 변환
    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .loginId(user.getLoginId())
                .userName(user.getUserName())
                .nickname(user.getNickname())
                .birthday(String.valueOf(user.getBirthday()))
                .gender(user.getGender())
                .location(user.getLocation())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }





}
