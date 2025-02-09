package history.traveler.rollingkorea.place.service.implementation;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.place.controller.request.LikePlaceAddRequest;
import history.traveler.rollingkorea.place.controller.response.LikePlaceResponse;
import history.traveler.rollingkorea.place.domain.LikePlace;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.LikePlaceRepository;
import history.traveler.rollingkorea.place.repository.PlaceRepository;
import history.traveler.rollingkorea.place.service.LikePlaceService;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_LIKEPLACE;
import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_PLACE;
import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_USER;
import static history.traveler.rollingkorea.global.error.ErrorCode.PLACE_IS_DUPLICATED;

@Service
@RequiredArgsConstructor
@Transactional
public class LikePlaceServiceImpl implements LikePlaceService {

    private final LikePlaceRepository likePlaceRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @Override
    public void addPlace(LikePlaceAddRequest likePlaceAddRequest) {

        User user = getUser();
        Place place = existPlaceCheck(likePlaceAddRequest.place().getPlaceId());

        //이미 존재하는 상품이면 delete처리
        if (likePlaceRepository.findByPlaceIdAndUser(place.getPlaceId(), user).isPresent())
            throw new BusinessException(PLACE_IS_DUPLICATED);

        LikePlace likePlace = LikePlace.builder()
                .user(user)
                .placeId(place.getPlaceId())
                .build();

        likePlaceRepository.save(likePlace);
    }

    @Override
    @Transactional(readOnly = true) /// 이 메서드는 트랜잭션을 사용하며, 읽기 전용으로 설정되어 있어 데이터 변경 작업을 하지 않음을 나타냅
    public Page<LikePlaceResponse> findLikePlaceUser(Pageable pageable) {

      User user = getUser();
      Page<LikePlace> likePlaces = likePlaceRepository.findAllByUser_UserId(user.getUserId(), pageable);
      return likePlaces.map(LikePlaceResponse::new);
    }

    @Override
    public void deleteLikePlace(Long likePlaceId) {
        User user = getUser();
        LikePlace likePlace = existUserLikePlaceCheck(likePlaceId, user);
        likePlaceRepository.deleteById(likePlace.getLikePlaceId());

    }

    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }

    //The code is tested
    private Place existPlaceCheck(Long placeId) {
        return placeRepository.findByPlaceId(placeId).orElseThrow(
                () -> new BusinessException(NOT_FOUND_PLACE));
    }

    private LikePlace existUserLikePlaceCheck(Long likePlaceId, User user) {
        return likePlaceRepository.findByPlaceIdAndUser(likePlaceId, user)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_LIKEPLACE));
    }
}
