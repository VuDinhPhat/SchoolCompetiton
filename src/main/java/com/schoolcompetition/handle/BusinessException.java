package com.schoolcompetition.handle;

import com.schoolcompetition.enums.ErrorMessage;

public class BusinessException extends RuntimeException {

    private String message;


    private BusinessException( String message) {
        super(message);
        this.message = message;
    }


    public BusinessException(ErrorMessage message) {
        super(message.getMessage());
        this.message = message.getMessage();
    }

}
