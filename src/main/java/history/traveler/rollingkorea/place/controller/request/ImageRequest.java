package history.traveler.rollingkorea.place.controller.request;
import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

public record ImageRequest(

        @Nullable MultipartFile imageData

) {

}
