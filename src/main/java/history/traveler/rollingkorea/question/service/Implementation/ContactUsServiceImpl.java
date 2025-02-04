package history.traveler.rollingkorea.question.service.Implementation;


import history.traveler.rollingkorea.comment.controller.response.CommentResponse;
import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.question.controller.request.ContactUsCreateRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsEditRequest;
import history.traveler.rollingkorea.question.controller.response.ContactUsResponse;
import history.traveler.rollingkorea.question.domain.ContactUs;
import history.traveler.rollingkorea.question.repository.ContactUsRepository;
import history.traveler.rollingkorea.question.service.ContactUsService;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static history.traveler.rollingkorea.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactUsServiceImpl implements ContactUsService {

    private final ContactUsRepository contactUsRepository;
    private final UserRepository userRepository;


    @Override
    public void createContactUs(ContactUsCreateRequest contactUsCreateRequest) {
        User user = getUser(); // bring user data
        if(user == null) {
            throw new IllegalArgumentException("user is null");
        }

        ContactUs contactUs = ContactUs.createContactUs(user, contactUsCreateRequest);
        contactUsRepository.save(contactUs);
    }

    @Override
    public void editContactUs(Long contactUsId, ContactUsEditRequest contactUsEditRequest) {
        User user = getUser();
        ContactUs contactUs = existContactUsCheck(contactUsId);
        writeContactUsUserEqualLoginUserCheck(user, contactUs);
        contactUs.editContactUs(contactUsEditRequest);
    }

    @Override
    public void deleteContactUs(Long contactUsId) {
        User user = getUser();
        ContactUs contactUs = existContactUsCheck(contactUsId);
        writeContactUsUserEqualLoginUserCheck(user, contactUs);
        contactUsRepository.delete(contactUs);

    }

    @Override
    public Page<CommentResponse> findByUser(Pageable pageable) {
        return Page.empty(pageable);
//        User user = getUser();
//        Page<ContactUs> contactUs = contactUsRepository.findByUser(user, pageable);
//        return contactUs.map(ContactUsResponse::new);

    }


    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            String loginId = authentication.getName(); // bring loginId

            return userRepository.findByLoginId(loginId) //search by loginId
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
        }

        return null;
    }


    private ContactUs existContactUsCheck(Long contactUsId) {
        return null;
//        return contactUsRepository.findById(contactUsId)
//                .orElseThrow(() -> new BusinessException(NOT_FOUND_CONTACTUS));
    }

    private void writeContactUsUserEqualLoginUserCheck(User user, ContactUs contactUs) {
        if(!contactUs.getUser().getLoginId().equals(user.getLoginId())) {
            throw new BusinessException(NOT_MATCH_CONTACTUS);
        }
    }
}
