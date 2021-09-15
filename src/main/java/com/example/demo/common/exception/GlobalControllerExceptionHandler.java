package com.example.demo.common.exception;

import com.example.demo.application.response.GeshiTestResponse;
import com.example.demo.application.response.SecBodyResp;
import com.example.demo.application.response.SquareCalculateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author chengp
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * 参数异常检查
     *
     * @param e 异常抛出
     * @return resp
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleException(MethodArgumentNotValidException e) {
//        SquareCalculateResponse resp = new SquareCalculateResponse();
//        resp.setRespCode("9999");
//        resp.setRespMsg(e.getBindingResult().getFieldError().getDefaultMessage());
//        return resp;

        GeshiTestResponse resp = new GeshiTestResponse();
        SecBodyResp bodyResp = new SecBodyResp();
        bodyResp.setData("9999");
        bodyResp.setSign(e.getBindingResult().getFieldError().getDefaultMessage());
        resp.setBody(bodyResp);
        return resp;
    }
}
