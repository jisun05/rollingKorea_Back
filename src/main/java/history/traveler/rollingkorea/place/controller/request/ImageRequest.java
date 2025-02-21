package history.traveler.rollingkorea.place.controller.request;
import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

public record ImageRequest(

        // 디비 사이즈에 맞게 밸리데이션 하는 법?
        @Nullable MultipartFile imageData

) {

}
