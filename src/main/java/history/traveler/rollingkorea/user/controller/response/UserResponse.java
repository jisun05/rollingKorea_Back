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


    private String loginId;         //회원 ID
    private String userName;            //이름
    private String nickname;
    private String birthday;
    private String gender;
    private String location;         //우편번호
    private String phoneNumber;           //핸드폰번호
    private List<String> roles;

    // Member -> MemberResponse 변환
    public static UserResponse toResponse(User m) {
        return UserResponse.builder()
                .loginId(m.getLoginId())
                .userName(m.getUserName())
                .nickname(m.getNickname())
                .birthday(String.valueOf(m.getBirthday()))
                .gender(m.getGender())
                .location(m.getLocation())
                .phoneNumber(m.getPhoneNumber())
                .build();
    }





}
