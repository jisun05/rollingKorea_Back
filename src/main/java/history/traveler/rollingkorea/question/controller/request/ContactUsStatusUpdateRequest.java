package history.traveler.rollingkorea.question.controller.request;

public record ContactUsStatusUpdateRequest(
        Long contactUsId,    // 상태를 업데이트할 ContactUs의 ID
        String status        // 상태를 나타내는 필드 (예: "답변 대기", "처리 완료" 등)
) {
    // 추가적인 필드나 메서드를 정의할 수 있습니다.
}
