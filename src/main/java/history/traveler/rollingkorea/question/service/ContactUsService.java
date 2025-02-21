package history.traveler.rollingkorea.question.service;

import history.traveler.rollingkorea.question.controller.request.ContactUsCreateRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsEditRequest;
import history.traveler.rollingkorea.question.controller.response.ContactUsCreateResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsEditResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsSearchResponse;
import history.traveler.rollingkorea.question.controller.response.FileResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ContactUsService {
    ContactUsCreateResponse createContactUs(Long userId, @Valid ContactUsCreateRequest contactUsCreateRequest);

    ContactUsEditResponse editContactUs(Long contactUsId, @Valid ContactUsEditRequest contactUsEditRequest);

    void deleteContactUs(Long contactUsId);

    Page<ContactUsSearchResponse> findByUser(Pageable pageable);

    ContactUsSearchResponse getContactUs(Long contactUsId);

    FileResponse getFileResponse(Long contactUsId);
}
