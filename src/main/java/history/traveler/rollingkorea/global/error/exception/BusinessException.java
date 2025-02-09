package history.traveler.rollingkorea.global.error.exception;

import history.traveler.rollingkorea.global.error.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        log.error("BusinessException 발생: {} - {}", errorCode.name(), errorCode.getMessage());
    }

}
