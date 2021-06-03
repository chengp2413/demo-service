package com.example.demo.domain.service;

import com.example.demo.application.request.GeshiTestRequest;
import com.example.demo.application.request.SquareCalculateRequest;
import com.example.demo.application.response.GeshiTestResponse;
import com.example.demo.application.response.SquareCalculateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 交易 处理
 */
@Service
@Slf4j
public class TransService {

    /**
     * 平方计算 处理
     *
     * @param request 请求
     * @return response
     */
    public SquareCalculateResponse squareCalculate(SquareCalculateRequest request) {
        log.info("==================domain层服务开始执行");
        SquareCalculateResponse response = new SquareCalculateResponse();
        int count = request.getCanShu() * request.getCanShu();
        response.setRespCode("0000");
        response.setRespMsg("交易成功");
        response.setResult(count);
        response.setTranCode(request.getTranCode());
        log.info("==================domain层服务执行结束");
        return response;
    }


    /**
     * 报文格式测试 处理
     *
     * @param request 请求
     * @return response
     */
    public GeshiTestResponse geshiTest(GeshiTestRequest request) {
        GeshiTestResponse response = new GeshiTestResponse();
        GeshiTestResponse.Header header = new GeshiTestResponse.Header();
        GeshiTestResponse.Body body = new GeshiTestResponse.Body();

        log.info("交易代码：{}",request.getHeader().getTranCode());
        log.info("格式：{}",request.getBody().getGeShi());

        header.setRespCode("0000");
        header.setRespMsg("交易成功");
        response.setHeader(header);
        body.setGeShi(request.getBody().getGeShi());
        response.setBody(body);

        return response;
    }
}
