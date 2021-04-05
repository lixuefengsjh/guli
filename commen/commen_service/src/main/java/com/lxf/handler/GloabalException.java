package com.lxf.handler;

import com.lxf.entity.GuliException;
import com.lxf.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GloabalException {
    @ExceptionHandler(GuliException.class)
    public R<String> catchException(GuliException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return  R.error().code(e.getCode()).data(e);
    }
}
