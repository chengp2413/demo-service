package com.example.demo.application.apis.wzsz;

import com.example.demo.application.request.GeshiTestRequest;
import com.example.demo.application.request.SecRequest;
import com.example.demo.application.request.SquareCalculateRequest;
import com.example.demo.application.response.GeshiTestResponse;
import com.example.demo.application.response.SecResponse;
import com.example.demo.application.response.SquareCalculateResponse;
import com.example.demo.domain.service.TransService;
import com.example.demo.domain.service.reqeust.GeshiTestBO;
import com.example.demo.domain.service.reqeust.SecBOHeaderReq;
import com.example.demo.domain.service.reqeust.SecTest2BOBody;
import com.example.demo.domain.service.reqeust.SecTest2BORequest;
import com.example.demo.domain.service.response.GeshiTest2Resp;
import com.example.demo.domain.service.response.SecBOResponse;
import com.example.demo.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 交易api
 *
 * @author chengp
 */
@Slf4j
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
     * 报文格式测试
     *
     * @param request 请求
     * @return response
     */
    @PostMapping("/geshiTest")
    public Object geshiTest(@RequestBody GeshiTestRequest request) {
        log.info("==================进入Api==================");
        log.info("api接受的参数:{}", request);
        GeshiTest2Resp response = transService.geshiTest(request);
        return response;
    }

    /**
     * 报文格式测试
     *
     * @param request 请求
     * @return response
     */
    @PostMapping("/geshiTest2")
    public Object geshiTest2(@RequestBody SecRequest request) {
        log.info("==================进入Api==================");
        log.info("api接收的参数{}", request);
        SecTest2BORequest boRequest = new SecTest2BORequest(); // 这句是
        boRequest.setHeader(JsonUtils.src2Obj(request.getHeader(), SecBOHeaderReq.class));
        boRequest.setBody(JsonUtils.jsonToBean(request.getBody().getData(), SecTest2BOBody.class));
        GeshiTest2Resp response = transService.geshiTest2(boRequest);
        return response;
    }


}