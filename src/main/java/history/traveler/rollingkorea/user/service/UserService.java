package history.traveler.rollingkorea.user.service;


import history.traveler.rollingkorea.user.controller.request.UserSignupRequest;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public Long save(UserSignupRequest dto){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .loginId(dto.loginId())
                .password(encoder.encode(dto.password()))
                .build()).getUserId();
    }

    public User findById(Long userId){
        return userRepository.findById(Long.valueOf(userId))  //userId 타입 문제 더 봐야함
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String loginId){
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User authenticate(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(user -> new BCryptPasswordEncoder().matches(password, user.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }
}
