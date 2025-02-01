package history.traveler.rollingkorea.question.service;

import history.traveler.rollingkorea.comment.controller.response.CommentResponse;
import history.traveler.rollingkorea.question.controller.request.ContactUsCreateRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsEditRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


public interface ContactUsService {
     void createContactUs(@Valid ContactUsCreateRequest contactUsCreateRequest);

    void editContactUs(Long contactUsId, @Valid ContactUsEditRequest contactUsEditRequest);


    void deleteContactUs(Long contactUsId);
    
    Page<CommentResponse> findByUser(Pageable pageable);


}
