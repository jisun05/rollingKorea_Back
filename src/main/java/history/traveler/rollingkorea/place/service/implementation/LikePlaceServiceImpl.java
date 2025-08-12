// src/main/java/history/traveler/rollingkorea/place/service/implementation/LikePlaceServiceImpl.java
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
import history.traveler.rollingkorea.user.repository.UserRepository;
import history.traveler.rollingkorea.user.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_PLACE;
import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class LikePlaceServiceImpl implements LikePlaceService {

    private final LikePlaceRepository likePlaceRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @Override
    public void manageLikePlace(Long userId, LikePlaceManageRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
        Place place = placeRepository.findByContentId(request.contentId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_PLACE));

        likePlaceRepository
                .findByPlace_ContentIdAndUser(request.contentId(), user)
                .ifPresentOrElse(
                        likePlaceRepository::delete,
                        () -> likePlaceRepository.save(LikePlace.createLikePlace(user, place))
                );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LikePlaceResponse> findAllByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
        return likePlaceRepository
                .findAllByUser_UserId(userId, pageable)
                .map(LikePlaceResponse::new);
    }
}
