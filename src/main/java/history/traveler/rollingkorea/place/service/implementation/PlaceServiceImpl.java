package history.traveler.rollingkorea.place.service.implementation;

import history.traveler.rollingkorea.global.error.ErrorCode;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.place.controller.request.PlaceCreateRequest;
import history.traveler.rollingkorea.place.controller.request.PlaceEditRequest;
import history.traveler.rollingkorea.place.controller.response.ImageResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceCreateResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceResponse;
import history.traveler.rollingkorea.place.controller.response.PlaceUpdateResponse;
import history.traveler.rollingkorea.place.domain.Image;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.ImageRepository;
import history.traveler.rollingkorea.place.repository.LikePlaceRepository;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import history.traveler.rollingkorea.place.service.PlaceService;
import history.traveler.rollingkorea.heritage.domain.Heritage;
import history.traveler.rollingkorea.heritage.repository.HeritageRepository;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final HeritageRepository heritageRepository;
    private final ImageRepository imageRepository;
    private final LikePlaceRepository likePlaceRepository;
    private final UserRepository userRepository;

    /**
     * areaCode(heritage 기준) 로 Place 목록 조회 + 좋아요 여부 포함
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlaceResponse> findByAreaCode(Integer areaCode, Pageable pageable) {
        // 로그인 사용자 ID 조회 (있으면, 없으면 null)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        if (auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            userId = userRepository
                    .findByLoginId(auth.getName())
                    .map(User::getUserId)
                    .orElse(null);
        }
        // 람다에서 사용하기 위해 사실상 final 변수로 복사
        final Long finalUserId = userId;

        Page<Place> places = placeRepository.findByAreaCode(areaCode, pageable);

        return places.map(place -> {
            boolean liked = false;
            if (finalUserId != null) {
                liked = likePlaceRepository
                        .findByPlace_ContentIdAndUser(
                                place.getContentId(),
                                User.builder().userId(finalUserId).build()
                        ).isPresent();
            }
            return PlaceResponse.from(place, imageRepository, liked);
        });
    }

    /**
     * 단일 Place 조회 (좋아요 여부 포함)
     */
    @Override
    @Transactional(readOnly = true)
    public PlaceResponse findPlaceByContentId(Long contentId) {
        Place place = placeRepository.findByContentId(contentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        // 로그인 여부 확인
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        if (auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            userId = userRepository
                    .findByLoginId(auth.getName())
                    .map(User::getUserId)
                    .orElse(null);
        }

        boolean liked = userId != null && likePlaceRepository
                .findByPlace_ContentIdAndUser(
                        contentId,
                        User.builder().userId(userId).build()
                ).isPresent();

        return PlaceResponse.from(place, imageRepository, liked);
    }

    /**
     * 외부 heritage API 데이터로 Place import
     */
    @Override
    public PlaceCreateResponse placeCreate(
            PlaceCreateRequest req,
            MultipartFile imageFile
    ) {
        Long contentId = req.contentId();
        // 중복 체크
        if (placeRepository.existsByContentId(contentId)) {
            throw new BusinessException(ErrorCode.DUPLICATE_PLACE);
        }

        // Heritage 데이터 조회
        Heritage heritage = heritageRepository.findById(contentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        // Place 엔티티 생성 & 저장
        Place place = Place.fromHeritage(heritage);
        placeRepository.save(place);

        // 이미지 파일 저장(Optional)
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                byte[] data = imageFile.getBytes();
                Image img = Image.builder()
                        .imageData(data)
                        .place(place)
                        .build();
                imageRepository.save(img);
            } catch (IOException e) {
                throw new RuntimeException("이미지 처리 중 오류가 발생했습니다.", e);
            }
        }

        return PlaceCreateResponse.from(place);
    }

    /**
     * Place의 mutable 필드(title, addr1, addr2 등) 수정
     */
    @Override
    public PlaceUpdateResponse placeUpdate(
            Long contentId,
            PlaceEditRequest req,
            MultipartFile imageFile
    ) {
        Place place = placeRepository.findByContentId(contentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        // 엔티티 필드 업데이트
        place.update(req);

        // 이미지 교체 로직
        if (imageFile != null && !imageFile.isEmpty()) {
            // 기존 이미지 모두 삭제
            imageRepository.deleteByPlace_ContentId(contentId);
            try {
                byte[] data = imageFile.getBytes();
                Image img = Image.builder()
                        .imageData(data)
                        .place(place)
                        .build();
                imageRepository.save(img);
            } catch (IOException e) {
                throw new RuntimeException("이미지 처리 중 오류가 발생했습니다.", e);
            }
        }

        // 응답 DTO 빌드
        // 응답 DTO 빌드
        return PlaceUpdateResponse.builder()
                .contentId(place.getContentId())
                .title(place.getTitle())
                .addr1(place.getAddr1())
                .addr2(place.getAddr2())
                .importedAt(place.getImportedAt())
                .imageList(
                        imageRepository
                                .findByPlace_ContentId(place.getContentId())
                                .stream()
                                .map(img -> new ImageResponse(
                                        img.getImageId(),
                                        "/api/places/" + place.getContentId() + "/images/" + img.getImageId()
                                ))
                                .collect(Collectors.toList())
                )
                .updatedAt(LocalDateTime.now())
                .build();

    }

    /**
     * Place 삭제 (이미지 먼저 삭제)
     */
    @Override
    public boolean placeDelete(Long contentId) {
        if (!placeRepository.existsByContentId(contentId)) {
            return false;
        }
        imageRepository.deleteByPlace_ContentId(contentId);
        placeRepository.deleteByContentId(contentId);
        return true;
    }
}
