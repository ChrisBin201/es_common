package com.chris.common.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonException extends RuntimeException {
    private int httpStatusCode;
    private String errorCode;
    private String message;
    private String param;
    private Object data;

    public CommonException(int httpStatusCode, String errorCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public CommonException(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }


    public CommonException(int httpStatusCode, String errorCode, String message, Object data) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public CommonException(int httpStatusCode, String errorCode, Object data) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.data = data;
    }

    public static String getErrorMessage(Throwable throwable) {
        String error;
        if (throwable instanceof CommonException) {
            CommonException commonException = (CommonException) throwable;
            error = commonException.getMessage();
        } else {
            error = throwable.getMessage();
        }

        return error;
    }

}
