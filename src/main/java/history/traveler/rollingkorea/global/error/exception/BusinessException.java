package history.traveler.rollingkorea.global.error.exception;

import history.traveler.rollingkorea.global.error.ErrorCode;

public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;


    public BusinessException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
