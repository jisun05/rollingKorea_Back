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

    private String ccsiName;     // title
    private String ccbaCtcdNm;   // addr1
    private String imageUrl;
    private String latitude;     // mapy
    private String longitude;    // mapx

    public Heritage(String ccsiName, String ccbaCtcdNm, String imageUrl, String latitude, String longitude) {
        this.ccsiName = ccsiName;
        this.ccbaCtcdNm = ccbaCtcdNm;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
