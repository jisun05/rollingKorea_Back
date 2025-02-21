package history.traveler.rollingkorea.comment.controller.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReplyCreateRequest(
        @NotNull(message = "write your reply")
        @Size(max = 500, message = "max size is 500")
        String content
) {}
