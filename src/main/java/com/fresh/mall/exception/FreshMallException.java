package com.fresh.mall.exception;

public class FreshMallException extends RuntimeException {
    private final Integer code;
    private final String message;

    public FreshMallException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public FreshMallException(FreshMallExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
