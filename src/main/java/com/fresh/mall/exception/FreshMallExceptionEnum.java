package com.fresh.mall.exception;

public enum FreshMallExceptionEnum {
    NEED_USER_NAME(10001,"Username Null"),
    NEED_PASSWORD(10002,"Password Null"),
    PASSWORD_TOO_SHORT(10003,"Short Password"),
    NAME_EXISTED(10004, "Username already existed"),
    INSERT_FAILED(10005, "Insert failed, try later"),
    WRONG_PASSWORD(10006,"Wrong Password"),
    SYSTEM_ERROR(20000, "System Error");


    Integer code;

    String msg;

    FreshMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
