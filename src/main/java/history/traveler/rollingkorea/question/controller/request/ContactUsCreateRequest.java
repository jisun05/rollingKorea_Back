package history.traveler.rollingkorea.question.controller.request;

public record ContactUsCreateRequest (
        Long userId,
        String content
){}