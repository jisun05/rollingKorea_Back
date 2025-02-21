package history.traveler.rollingkorea.question.controller.request;


import jakarta.validation.constraints.NotBlank;

public record ContactUsAnswerRequest(
        @NotBlank(message = "Reply content must not be blank")
        String content
) {}
