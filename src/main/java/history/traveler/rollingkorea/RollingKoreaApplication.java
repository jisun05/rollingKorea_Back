package history.traveler.rollingkorea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
//@EnableMethodSecurity   // @PreAuthorize 활성화
@EnableJpaAuditing
@SpringBootApplication
@EnableAsync           // @Async 를 활성화
@EnableScheduling     // @Scheduled 을 활성화
public class RollingKoreaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RollingKoreaApplication.class, args);
    }

}
     