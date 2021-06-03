package com.example.demo.application.aspect;

import com.example.demo.application.response.ComResponse;
import com.example.demo.application.response.SquareCalculateResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Api切面
 */
@Aspect
@Slf4j
@Component
@Order(7)
public class ApiAspect {

    /**
     * Api切面层
     */
    @Pointcut("execution(* com.example.demo.application.apis..*.*(..))")
    public void apiPointCut() {
        //Do nothing because of doAround
    }

    /**
     * 循环增强
     */
    @Around(value = "apiPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("==================Api切面开始执行");
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
            return result;
        } catch (Exception e) {
            ComResponse c = new ComResponse();
            c.setRespCode("9999");
            c.setRespMsg(e.getMessage());
            return c;
        } finally {
            long endTime = System.currentTimeMillis();
            long count = endTime - startTime;
            log.info("==================Api切面执行结束");
            log.info("==================交易执行时间：{}ms", count);
        }
    }
}
