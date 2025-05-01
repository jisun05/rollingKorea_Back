package history.traveler.rollingkorea.heritage.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Heritage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ccsiName;
    private String ccbaCtcdNm;
    private String imageUrl;
    private String latitude;
    private String longitude;

    public Heritage(String ccsiName, String ccbaCtcdNm, String imageUrl, String latitude, String longitude) {
        this.ccsiName = ccsiName;
        this.ccbaCtcdNm = ccbaCtcdNm;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
