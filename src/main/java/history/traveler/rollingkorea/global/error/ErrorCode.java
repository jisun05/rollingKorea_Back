package history.traveler.rollingkorea.global.error;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {



    //user
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "User not found"),
    NOT_EQUAL_PASSWORD(HttpStatus.BAD_REQUEST,"Check your password"),
    DUPLICATED_LOGIN_ID(HttpStatus.BAD_REQUEST, "This ID already exists."),



    //permission


    //place
    NOT_FOUND_PLACE(HttpStatus.NOT_FOUND, "Place is not found."),
    DUPLICATE_PLACE(HttpStatus.BAD_REQUEST, "This place already exists."),
    DUPLICATE_LOCATION(HttpStatus.BAD_REQUEST,"There's already a registered place at this location."),
    //place_likes
    PLACE_IS_DUPLICATED(HttpStatus.NOT_FOUND, "You already have it."),
    NOT_FOUND_LIKEPLACE(HttpStatus.NOT_FOUND, "Not found your save place."),


    //comment
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "Comment not found."),
    NOT_MATCH_COMMENT(HttpStatus.BAD_REQUEST, "This is not the comment you wrote."),


    //reply

    NOT_FOUND_REPLY(HttpStatus.NOT_FOUND, "Reply not found."),
    NOT_MATCH_REPLY(HttpStatus.BAD_REQUEST, "This is not the reply you wrote."),


    //contactUs
    NOT_FOUND_CONTACTUS(HttpStatus.NOT_FOUND, "Question not found"),
    NOT_MATCH_CONTACTUS(HttpStatus.BAD_REQUEST, "This is not the question you wrote.");

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String message;



}
