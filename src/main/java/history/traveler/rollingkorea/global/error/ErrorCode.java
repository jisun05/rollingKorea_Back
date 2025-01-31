package history.traveler.rollingkorea.global.error;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {



    //user






    //permission


    //place


    //place_likes


    //comment
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "Comment not found."),
    NOT_MATCH_COMMENT(HttpStatus.BAD_REQUEST, "This is not the comment you wrote.");


    //reply





    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String message;



}
