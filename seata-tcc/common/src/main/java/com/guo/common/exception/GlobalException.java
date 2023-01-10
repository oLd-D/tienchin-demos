package com.guo.common.exception;

import com.guo.common.RespBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.PublicKey;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBean runTimeException(RuntimeException e){
        return RespBean.error(e.getMessage());
    }
}
