package history.traveler.rollingkorea.question.service.Implementation;

import history.traveler.rollingkorea.global.error.exception.BusinessException;
import history.traveler.rollingkorea.question.controller.request.ContactUsAnswerEditRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsAnswerRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsCreateRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsEditRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsStatusUpdateRequest;
import history.traveler.rollingkorea.question.controller.response.ContactUsAnswerEditResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsAnswerResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsCreateResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsEditResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsSearchResponse;
import history.traveler.rollingkorea.question.controller.response.FileResponse;
import history.traveler.rollingkorea.question.domain.ContactUs;
import history.traveler.rollingkorea.question.domain.File;
import history.traveler.rollingkorea.question.repository.ContactUsRepository;
import history.traveler.rollingkorea.question.service.ContactUsService;
import history.traveler.rollingkorea.user.domain.User;
import history.traveler.rollingkorea.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_CONTACTUS;
import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_FOUND_FILE;
import static history.traveler.rollingkorea.global.error.ErrorCode.NOT_MATCH_CONTACTUS;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactUsServiceImpl implements ContactUsService {

    private final ContactUsRepository contactUsRepository;
    private final UserRepository userRepository;

    @Operation(summary = "Create a new contact message", description = "Allows users to create a new contact message.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactUsCreateResponse createContactUs(@RequestParam Long userId, @RequestBody @Valid ContactUsCreateRequest request) throws IOException {
        // 파일 데이터 처리
        File file = null;
        if (request.file() != null && !request.file().isEmpty()) {
            file = new File(request.file().getBytes(), request.file().getOriginalFilename());
        }

        // ContactUs 객체 생성
        ContactUs contactUs = ContactUs.builder()
                .user(new User(userId))
                .content(request.content())
                .parentId(null)
                .listOrder(0L)
                .file(file)
                .build();

        // 저장 로직
        contactUsRepository.save(contactUs);

        // ContactUsCreateResponse 반환
        return ContactUsCreateResponse.fromContactUs(contactUs);
    }

    @Override
    public ContactUsEditResponse editContactUs(Long contactUsId, ContactUsEditRequest contactUsEditRequest) {
        User user = getUser();
        ContactUs contactUs = existContactUsCheck(contactUsId);
        writeContactUsUserEqualLoginUserCheck(user, contactUs);
        contactUs.editContactUs(contactUsEditRequest);
        return ContactUsEditResponse.from(contactUs);
    }

    @Override
    public void deleteContactUs(Long contactUsId) {
        User user = getUser();
        ContactUs contactUs = existContactUsCheck(contactUsId);
        writeContactUsUserEqualLoginUserCheck(user, contactUs);
        contactUsRepository.delete(contactUs);
    }

    @Override
    public Page<ContactUsSearchResponse> findByUser(Pageable pageable) {
        User user = getUser();
        Page<ContactUs> contactUs = contactUsRepository.findByUser(user, pageable);
        return contactUs.map(ContactUsSearchResponse::from); // Map each ContactUs to ContactUsSearchResponse
    }



    @Override
    public ContactUsSearchResponse getContactUs(Long contactUsId) {
        ContactUs contactUs = existContactUsCheck(contactUsId);
        return ContactUsSearchResponse.from(contactUs);
    }

    @Override
    public ContactUsAnswerResponse answerToContactUs(Long contactUsId, ContactUsAnswerRequest request) {
        ContactUs contactUs = existContactUsCheck(contactUsId);
        contactUs.answerToContactUs(request);
        return ContactUsAnswerResponse.from(contactUs);
    }

    @Override
    public ContactUsAnswerEditResponse editAnswer(Long contactUsId, ContactUsAnswerEditRequest request) {
        return null;
    }

    @Override
    public void deleteAnswer(Long contactUsId) {
        ContactUs contactUs = existContactUsCheck(contactUsId);
        contactUs.deleteAnswer();
    }

    @Override
    public void updateStatus(Long contactUsId, ContactUsStatusUpdateRequest request) {
        ContactUs contactUs = existContactUsCheck(contactUsId);
        contactUs.updateStatus(request);
    }

    @Override
    public FileResponse getFileResponse(Long contactUsId) {
        ContactUs contactUs = existContactUsCheck(contactUsId);
        File file = contactUs.getFile(); // Embedded File 객체 가져오기

        if (file == null || file.getFileData() == null || file.getFileName() == null) {
            throw new BusinessException(NOT_FOUND_FILE);
        }

        return new FileResponse(file.getFileData(), file.getFileName());
    }

    private User getUser() {
        return User.builder()
                .userId(1L)
                .loginId("jisunnala@gmail.com")
                .nickname("TestUser")
                .build();
    }

    private ContactUs existContactUsCheck(Long contactUsId) {
        return contactUsRepository.findById(contactUsId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_CONTACTUS));
    }

    private void writeContactUsUserEqualLoginUserCheck(User user, ContactUs contactUs) {
        if (!contactUs.getUser().getLoginId().equals(user.getLoginId())) {
            throw new BusinessException(NOT_MATCH_CONTACTUS);
        }
    }
}
