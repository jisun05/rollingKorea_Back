package history.traveler.rollingkorea.question.controller.request;

public record ContactUsAnswerEditRequest(
        Long contactUsId,     // 답변 수정할 ContactUs의 ID
        String content,       // 수정할 내용
        byte[] fileData,      // 수정할 파일 데이터
        String fileName       // 수정할 파일 이름
) {
    // 추가적인 필드나 메서드를 정의할 수 있습니다.
}
