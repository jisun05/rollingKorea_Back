package history.traveler.springbootdeveloper.service;


import history.traveler.springbootdeveloper.domain.User;
import history.traveler.springbootdeveloper.dto.AddUserRequest;
import history.traveler.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public Long save(AddUserRequest dto){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    public User findById(Long userId){
        return userRepository.findById(userId)
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
