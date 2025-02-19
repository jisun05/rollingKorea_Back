package history.traveler.rollingkorea.question.controller.request;

public record ContactUsAnswerRequest(
        Long contactUsId,    // 답변할 ContactUs의 ID
        String content,      // 답변 내용
        Long parentId,       // 부모 ID (필요한 경우)
        Long listOrder,      // 리스트 순서
        byte[] fileData,     // 파일 데이터
        String fileName      // 파일 이름
) {

}
