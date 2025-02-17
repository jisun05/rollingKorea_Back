package history.traveler.rollingkorea.place.service.implementation;

import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.place.controller.request.LikePlaceManageRequest;
import history.traveler.rollingkorea.place.controller.response.LikePlaceResponse;
import history.traveler.rollingkorea.place.domain.LikePlace;
import history.traveler.rollingkorea.place.domain.Place;
import history.traveler.rollingkorea.place.repository.ImageRepository;
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
    private final ImageRepository imageRepository;

    @Transactional
    @Override
    public void manageLikePlace(Long userId, LikePlaceManageRequest request) {
        User user = getUser();
        Place place = existPlaceCheck(request.placeId());

        // place와 user 값이 모두 유효한지 확인
        if (place == null) {
            throw new IllegalArgumentException("존재하지 않는 placeId입니다.");
        }

        if (user == null) {
            throw new IllegalArgumentException("유효하지 않은 사용자입니다.");
        }
        System.out.println("Place ID111: " + place.getPlaceId());
        System.out.println("Place ID222: " + request.placeId());

        likePlaceRepository.findByPlace_PlaceIdAndUser(request.placeId(), user)
                .ifPresentOrElse(likePlaceRepository::delete, () -> {
                    // LikePlace 객체 생성
                    LikePlace likePlace = LikePlace.createLikePlace(user, place);
                    // 로그로 LikePlace 객체의 값 확인
                    System.out.println("LikePlace created with placeId: " + likePlace.getPlace().getPlaceId());
                    likePlaceRepository.save(likePlace);
                });
    }


    @Override
    @Transactional(readOnly = true)
    public Page<LikePlaceResponse> findAllByUser(Long userId, Pageable pageable) {
         User user = getUser();

        Page<LikePlace> likePlaces = likePlaceRepository.findAllByUser_UserId(user.getUserId() ,pageable);

        return likePlaces.map(LikePlaceResponse::new);
    }





//test를 위한 주석처리
//    private User getUser(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return userRepository.findByLoginId(authentication.getName()).orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
//    }

    private User getUser() {
        // 🔥 테스트용 더미 유저 추가 (로그인 없이 Swagger 테스트 가능)
        return User.builder()
                .userId(1L)
                .loginId("jisunnala@gmail.com")
                .nickname("TestUser")
                .build();
    }

    //The code is tested
    private Place existPlaceCheck(Long placeId) {
        return placeRepository.findByPlaceId(placeId).orElseThrow(
                () -> new BusinessException(NOT_FOUND_PLACE));
    }


}
