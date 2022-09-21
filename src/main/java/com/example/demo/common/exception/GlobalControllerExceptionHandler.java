package com.example.demo.common.exception;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    public Object handleException(HttpServletRequest request, MethodArgumentNotValidException e) {
        try (InputStreamReader reader = new InputStreamReader(request.getInputStream())) {
            log.info("请求路径=====> {}", request.getRequestURI());
            log.info("请求报文=====> {}", IoUtil.read(reader));
            String message = e.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getField() + " is required").collect(Collectors.joining(","));
            String returnMsg = e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
            log.info("参数检查=====> {}", message);
            Map<String, String> map = new HashMap<>();
            map.put("returnCode", "9999");
            map.put("returnMsg", returnMsg);
            log.info("响应报文=====> {}", JSONUtil.toJsonStr(map));
            return map;
        } catch (Exception e2) {
            return e2;
        }
    }
}
