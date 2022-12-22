package com.programming.dmaker.exception;

import com.programming.dmaker.dto.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

// 모든 Controller의 exception을 받아서 한 군데서 처리!
// 에러 메세지를 Control 할 수 있다는 장점.
// 너무 자세한 에러는 보안 이슈를 야기할 수도 있다.
@Slf4j
@RestControllerAdvice // controller에 advice역할을 하는  annotation
public class DMakerExceptionHandler {

    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(
            DMakerException e,
            HttpServletRequest request
    ) {
        log.error("errorCode: {}, url : {}, message : {}", e.getDMakerErrorCode(),request.getRequestURI() , e.getDetailMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    public DMakerErrorResponse handleBadRequest(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("url : {}, message : {}", request.getRequestURI() , e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(DMakerErrorCode.INVALID_REQUEST)
                .errorMessage(e.getMessage())
                .build();
    }


    @ExceptionHandler(Exception.class)
    public DMakerErrorResponse handleException(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("url : {}, message : {}", request.getRequestURI() , e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(DMakerErrorCode.INTERNAL_SERVER_ERROR)
                .errorMessage(DMakerErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
