package history.traveler.rollingkorea.place.service.implementation;

import history.traveler.rollingkorea.global.error.ErrorCode;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import history.traveler.rollingkorea.place.controller.response.PlaceCreateResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceUpdateResponse;
import history.traveler.rollingkorea.place.domain.Image;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.ImageRepository;
import history.traveler.rollingkorea.place.repository.LikePlaceRepository;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import history.traveler.rollingkorea.place.service.PlaceService;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final LikePlaceRepository likePlaceRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<PlaceResponse> findByRegion(String region, Pageable pageable) {
        // 로그인 여부 확인
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;

        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            String loginId = auth.getName();
            User user = userRepository.findByLoginId(loginId).orElse(null);
            if (user != null) {
                userId = user.getUserId();
            }
        }

        Long finalUserId = userId; // 람다에서 사용

        Page<Place> places = placeRepository.findByRegion(pageable, region);

        return places.map(place -> {
            boolean liked = false;
            if (finalUserId != null) {
                liked = likePlaceRepository.findByPlace_PlaceIdAndUser(
                        place.getPlaceId(),
                        User.builder().userId(finalUserId).build()
                ).isPresent();
            }
            // 비회원은 liked=false
            return PlaceResponse.from(place, imageRepository, liked);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public PlaceResponse findPlaceById(Long placeId) {
        Place place = placeRepository.findByPlaceId(placeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        // 로그인 여부 확인
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;

        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            String loginId = auth.getName();
            User user = userRepository.findByLoginId(loginId).orElse(null);
            if (user != null) {
                userId = user.getUserId();
            }
        }

        boolean liked = false;
        if (userId != null) {
            liked = likePlaceRepository.findByPlace_PlaceIdAndUser(
                    place.getPlaceId(),
                    User.builder().userId(userId).build()
            ).isPresent();
        }

        return PlaceResponse.from(place, imageRepository, liked);
    }

    @Override
    @Transactional
    public PlaceUpdateResponse placeUpdate(Long id, PlaceEditRequest placeEditRequest, MultipartFile imageFile) {
        Place place = placeRepository.findByPlaceId(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        place.update(placeEditRequest);

        if (imageFile != null && !imageFile.isEmpty()) {
            imageRepository.deleteByPlace_PlaceId(id);
            try {
                byte[] newImageData = imageFile.getBytes();
                Image image = Image.builder()
                        .imageData(newImageData)
                        .place(place)
                        .build();
                imageRepository.save(image);
            } catch (IOException e) {
                throw new RuntimeException("이미지 파일 처리 중 오류가 발생했습니다.", e);
            }
        }

        return PlaceUpdateResponse.builder()
                .placeId(place.getPlaceId())
                .placeName(place.getPlaceName())
                .region(place.getRegion())
                .placeDescription(place.getPlaceDescription())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .imageList(imageRepository.findByPlace_PlaceId(place.getPlaceId()))
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public boolean placeDelete(Long id) {
        if (placeRepository.existsById(id)) {
            imageRepository.deleteByPlace_PlaceId(id);
            placeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public PlaceCreateResponse placeCreate(PlaceCreateRequest placeCreateRequest, MultipartFile imageFile) {
        if (placeRepository.findByPlaceName(placeCreateRequest.placeName()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_PLACE);
        }

        if (placeRepository.findByLatitudeAndLongitude(placeCreateRequest.latitude(), placeCreateRequest.longitude()).isPresent()) {
            throw new BusinessException(ErrorCode.DUPLICATE_LOCATION);
        }

        Place place = Place.create(placeCreateRequest);
        placeRepository.save(place);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                byte[] imageData = imageFile.getBytes();
                Image image = Image.builder()
                        .imageData(imageData)
                        .place(place)
                        .build();
                imageRepository.save(image);
            } catch (IOException e) {
                throw new RuntimeException("이미지 파일 처리 중 오류가 발생했습니다.", e);
            }
        }

        return PlaceCreateResponse.from(place);
    }
}
