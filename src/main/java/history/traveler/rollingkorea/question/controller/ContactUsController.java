package history.traveler.rollingkorea.question.controller;
import history.traveler.rollingkorea.comment.controller.response.CommentResponse;
import history.traveler.rollingkorea.question.controller.request.ContactUsCreateRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsEditRequest;
import history.traveler.rollingkorea.question.service.ContactUsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ContactUsController {

    private final ContactUsService contactUsService;

    @Operation(summary = "Create a new contact message", description = "Allows users to create a new contact message.")
    @PostMapping("/contact-us")
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAnyRole('USER')")
    public void createContactUs(Long userId, @RequestBody @Valid ContactUsCreateRequest contactUsCreateRequest) {
        contactUsService.createContactUs(contactUsCreateRequest);
    }

    @Operation(summary = "Edit an existing contact message", description = "Allows users to edit their contact messages.")
    @PutMapping("/contact-us/{contactUsId}")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAnyRole('USER')")
    public void editContactUs(@PathVariable("contactUsId") Long contactUsId, @RequestBody @Valid ContactUsEditRequest contactUsEditRequest) {
        contactUsService.editContactUs(contactUsId, contactUsEditRequest);
    }

    @Operation(summary = "Delete a contact message", description = "Allows admin to delete a contact message by ID.")
    @DeleteMapping("/contact-us/{contactUsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteContactUs(@PathVariable("contactUsId") Long contactUsId) {
        contactUsService.deleteContactUs(contactUsId);
    }

    @Operation(summary = "Retrieve all contact messages", description = "Fetches all contact messages with pagination support.")
    @GetMapping("/contact-us")
    public Page<CommentResponse> findByUser(
            @PageableDefault(sort = "contactUsId", direction = Sort.Direction.DESC) Pageable pageable) {
        return contactUsService.findByUser(pageable);
    }
}
