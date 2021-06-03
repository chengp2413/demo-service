package com.example.demo.application.apis;

import com.example.demo.application.request.GeshiTestRequest;
import com.example.demo.application.request.SquareCalculateRequest;
import com.example.demo.application.response.GeshiTestResponse;
import com.example.demo.application.response.SquareCalculateResponse;
import com.example.demo.domain.service.TransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * 交易api
 *
 * @author chengp
 */
@RestController
@Validated
@RequestMapping("/demo")
public class TransApi {

    /**
     * 注入服务
     */
    @Autowired
    private TransService transService;

    /**
     * 平方计算
     *
     * @param request 请求
     * @return response
     */
    @PostMapping("/squareCalculate")
    public Object squareCalculate(@Valid @RequestBody SquareCalculateRequest request) {
        SquareCalculateResponse response = transService.squareCalculate(request);
        return response;
    }

    /**
     * 报文格式测试
     *
     * @param request 请求
     * @return response
     */
    @PostMapping("/geshiTest")
    public Object geshiTest(@Valid @RequestBody GeshiTestRequest request) {
        GeshiTestResponse response = transService.geshiTest(request);
        return response;
    }
}