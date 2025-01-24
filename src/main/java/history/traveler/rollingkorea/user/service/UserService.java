package history.traveler.rollingkorea.user.service;


import history.traveler.rollingkorea.user.domain.AddUserRequest;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public String save(AddUserRequest dto){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getUserId();
    }

    public User findById(String userId){
        return userRepository.findById(Long.valueOf(userId))  //userId 타입 문제 더 봐야함
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> new BCryptPasswordEncoder().matches(password, user.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }
}
