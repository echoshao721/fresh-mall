package com.fresh.mall.exception;

import com.fresh.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e){
        log.error("Default Exception: ",e);
        return ApiRestResponse.error(FreshMallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(FreshMallException.class)
    @ResponseBody
    public Object handleFreshMallException(FreshMallException e){
        log.error("Fresh Mall Exception: ",e);
        return ApiRestResponse.error(e.getCode(),e.getMessage());
    }
}
