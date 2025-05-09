package history.traveler.rollingkorea.question.controller;

import history.traveler.rollingkorea.question.controller.request.ContactUsAnswerRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsCreateRequest;
import history.traveler.rollingkorea.question.controller.request.ContactUsEditRequest;
import history.traveler.rollingkorea.question.controller.response.ContactUsAnswerResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsCreateResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsEditResponse;
import history.traveler.rollingkorea.question.controller.response.ContactUsSearchResponse;
import history.traveler.rollingkorea.question.controller.response.FileResponse;
import history.traveler.rollingkorea.question.domain.ContactUs;
import history.traveler.rollingkorea.question.domain.File;
import history.traveler.rollingkorea.question.repository.ContactUsRepository;
import history.traveler.rollingkorea.question.service.ContactUsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact-us")
public class ContactUsController {

    private final ContactUsService contactUsService;
    private final ContactUsRepository contactUsRepository;

    @Operation(summary = "Create a new contact message", description = "Allows users to create a new contact message.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "multipart/form-data")
    public ContactUsCreateResponse createContactUs(@RequestParam Long userId,
                                                   @RequestPart("content") String content,
                                                   @RequestPart("file") MultipartFile file){

        // ContactUsCreateRequest 객체 생성
        ContactUsCreateRequest request = new ContactUsCreateRequest(content, file);

        // 응답 반환
        return contactUsService.createContactUs(userId, request);
    }

    @Operation(summary = "Retrieve all contact messages", description = "Fetches all contact messages with pagination support.")
    @GetMapping
    public Page<ContactUsSearchResponse> getContactUsList(
            @PageableDefault(sort = "contactUsId", direction = Sort.Direction.DESC) Pageable pageable) {
        return contactUsService.findByUser(pageable);
    }

    @Operation(summary = "Retrieve a specific contact message", description = "Fetches a specific contact message by ID.")
    @GetMapping("/{contactUsId}")
    public ResponseEntity<ContactUsSearchResponse> getContactUs(@PathVariable Long contactUsId) {
        return ResponseEntity.ok(contactUsService.getContactUs(contactUsId));
    }

    @Operation(summary = "Edit an existing contact message", description = "Allows users to edit their contact messages.")
    @PutMapping("/{contactUsId}")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAnyRole('USER')")
    public ContactUsEditResponse editContactUs(@PathVariable Long contactUsId, @RequestBody @Valid ContactUsEditRequest request) {
        return contactUsService.editContactUs(contactUsId, request);
    }



    @Operation(summary = "Delete a contact message", description = "Allows users to delete their contact messages.")
    @DeleteMapping("/{contactUsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyRole('USER')")
    public void deleteContactUs(@PathVariable Long contactUsId) {
        contactUsService.deleteContactUs(contactUsId);
    }

    @Operation(summary = "Upload file for a contact message", description = "Allows users to upload a file for their contact message.")
    @PostMapping("/{contactUsId}/upload")
    public FileResponse uploadFile(@PathVariable Long contactUsId, @RequestParam("file") MultipartFile file) throws IOException {
        Blob fileData;
        try {
            fileData = new SerialBlob(file.getBytes());
        } catch (SQLException e) {
            throw new IOException("Error converting file bytes to Blob", e);
        }
        String fileName = file.getOriginalFilename();

        ContactUs contactUs = contactUsRepository.findById(contactUsId)
                .orElseThrow(() -> new RuntimeException("ContactUs not found with id: " + contactUsId));

        // 새로운 File 객체를 생성하고 엔티티의 파일 정보를 업데이트
        File fileEntity = new File(fileData, fileName);
        contactUs.updateFile(fileEntity);

        // 엔티티 저장
        contactUsRepository.save(contactUs);

        return new FileResponse(fileData, fileName);
    }

    @Operation(summary = "Download file for a contact message", description = "Allows users to download a file attached to their contact message.")
    @GetMapping("/{contactUsId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long contactUsId) {
        try {
            // 서비스에서 FileResponse (Blob과 파일명이 담긴 레코드)를 가져옴
            FileResponse fileResponse = contactUsService.getFileResponse(contactUsId);

            // Blob 데이터를 byte 배열로 변환
            Blob fileBlob = fileResponse.fileData();
            byte[] fileBytes = fileBlob.getBytes(1, (int) fileBlob.length());

            // byte 배열을 ByteArrayResource로 감싸서 리소스 생성
            ByteArrayResource resource = new ByteArrayResource(fileBytes);

            // FileResponse에서 제공하는 헤더 생성 메서드 사용
            HttpHeaders headers = FileResponse.getFileHeaders(fileResponse);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileBytes.length)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
            summary = "Admin/User replies to a contact message",
            description = "Allows administrators to reply to a user's inquiry by creating a new ContactUs record with a parent reference"
    )
    @PostMapping("/{contactUsId}/reply")
    @ResponseStatus(HttpStatus.OK)
    public ContactUsAnswerResponse respondToContactUs(@PathVariable Long contactUsId,
                                                  @RequestBody @Valid ContactUsAnswerRequest request) {
        return contactUsService.answerContactUs(contactUsId, request);
    }

    @Operation(
            summary = "Retrieve all replies for a contact message",
            description = "Retrieves all direct and nested replies for the specified contact message id."
    )
    @GetMapping("/{contactUsId}/replies")
    public ResponseEntity<List<ContactUsSearchResponse>> getReplies(@PathVariable Long contactUsId) {
        List<ContactUs> replies = contactUsService.getAllReplies(contactUsId);
        List<ContactUsSearchResponse> response = replies.stream()
                .map(ContactUsSearchResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }



}
