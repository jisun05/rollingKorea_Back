package history.traveler.rollingkorea.place.service.implementation;

import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.place.controller.request.LikePlaceManageRequest;
import history.traveler.rollingkorea.place.controller.response.LikePlaceResponse;
import history.traveler.rollingkorea.place.domain.LikePlace;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.LikePlaceRepository;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import history.traveler.rollingkorea.place.service.LikePlaceService;
import history.traveler.rollingkorea.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_PLACE;

@Service
@RequiredArgsConstructor
@Transactional
public class LikePlaceServiceImpl implements LikePlaceService {

    private final LikePlaceRepository likePlaceRepository;
    private final PlaceRepository placeRepository;

    /**
     * 사용자-장소 좋아요 토글 처리
     */
    @Override
    public void manageLikePlace(Long userId, LikePlaceManageRequest request) {
        User user = getUser();  // 실제론 userId로 조회
        Place place = existPlaceCheck(request.contentId());

        // 로그 출력 (테스트용)
        System.out.println("DB에서 조회된 contentId: " + place.getContentId());
        System.out.println("요청으로 들어온 contentId: " + request.contentId());

        likePlaceRepository
                .findByPlace_ContentIdAndUser(request.contentId(), user)
                .ifPresentOrElse(
                        likePlaceRepository::delete,
                        () -> {
                            LikePlace likePlace = LikePlace.createLikePlace(user, place);
                            System.out.println("LikePlace 생성, contentId: " + likePlace.getPlace().getContentId());
                            likePlaceRepository.save(likePlace);
                        }
                );
    }

    /**
     * 유저가 좋아요 누른 장소 목록 조회 (페이징)
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LikePlaceResponse> findAllByUser(Long userId, Pageable pageable) {
        User user = getUser();  // 실제론 userId로 조회
        Page<LikePlace> likePlaces = likePlaceRepository.findAllByUser_UserId(user.getUserId(), pageable);
        return likePlaces.map(LikePlaceResponse::new);
    }

    // --- 헬퍼 메서드 ---

    private User getUser() {
        // 🔥 테스트용 더미 유저 (실제론 userId 기반으로 UserRepository에서 꺼내오기)
        return User.builder()
                .userId(1L)
                .loginId("test@example.com")
                .nickname("TestUser")
                .build();
    }

    private Place existPlaceCheck(Long contentId) {
        return placeRepository
                .findByContentId(contentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_PLACE));
    }
}
