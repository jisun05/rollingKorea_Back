package history.traveler.rollingkorea.question.service;

import history.traveler.rollingkorea.question.controller.request.ContactUsAnswerRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsCreateRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsEditRequest;
import history.traveler.rollingkorea.question.controller.response.ContactUsAnswerResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsCreateResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsEditResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsSearchResponse;
import history.traveler.rollingkorea.question.controller.response.FileResponse;
import history.traveler.rollingkorea.question.domain.ContactUs;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ContactUsService {
    ContactUsCreateResponse createContactUs(Long userId, @Valid ContactUsCreateRequest contactUsCreateRequest);

    ContactUsEditResponse editContactUs(Long contactUsId, @Valid ContactUsEditRequest contactUsEditRequest);

    void deleteContactUs(Long contactUsId);

    Page<ContactUsSearchResponse> findByUser(Pageable pageable);

    ContactUsSearchResponse getContactUs(Long contactUsId);

    FileResponse getFileResponse(Long contactUsId);

    ContactUsAnswerResponse answerContactUs(Long contactUsId, ContactUsAnswerRequest request);

    List<ContactUs> getAllReplies(Long contactUsId);
}
