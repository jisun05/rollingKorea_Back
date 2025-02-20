package history.traveler.rollingkorea.question.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;


public interface ContactUsService {
    ContactUsCreateResponse createContactUs(Long userId, @Valid ContactUsCreateRequest contactUsCreateRequest);

    ContactUsEditResponse editContactUs(Long contactUsId, @Valid ContactUsEditRequest contactUsEditRequest);


    void deleteContactUs(Long contactUsId);
    
    Page<ContactUsSearchResponse> findByUser(Pageable pageable);


    ContactUsSearchResponse getContactUs(Long contactUsId);

    ContactUsAnswerResponse answerToContactUs(Long contactUsId, @Valid ContactUsAnswerRequest request);

    ContactUsAnswerEditResponse editAnswer(Long contactUsId, @Valid ContactUsAnswerEditRequest request);

    void deleteAnswer(Long contactUsId);

    void updateStatus(Long contactUsId, @Valid ContactUsStatusUpdateRequest request);

    FileResponse getFileResponse(Long contactUsId);
}
