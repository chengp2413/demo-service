package com.example.demo.domain.service;

import com.example.demo.application.response.*;
import com.example.demo.application.request.GeshiTestRequest;
import com.example.demo.application.request.SquareCalculateRequest;
import com.example.demo.domain.po.DemoComCzlsbPO;
import com.example.demo.domain.repository.DemoComCzlsbRepo;
import com.example.demo.domain.service.reqeust.SecTest2BORequest;
import com.example.demo.domain.service.response.GeshiTest2Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.validation.Valid;
import java.util.List;

/**
 * 交易处理
 */
@Service
@Slf4j
public class TransService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DemoComCzlsbRepo demoComCzlsbRepo;

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
        List<DemoComCzlsbPO> demoComCzlsbPOS = demoComCzlsbRepo.list();
        response.setRespCode("0000");
        response.setRespMsg(demoComCzlsbPOS.get(0).getPtls());
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
    public GeshiTest2Resp geshiTest(@Valid GeshiTestRequest request) {
        log.info("==================进入service==================");
        log.info("service接收的参数:{}", request);
        log.info("service获取参数name:{}", request.getName());

        // 业务处理

        GeshiTest2Resp response = new GeshiTest2Resp();
        response.setReturnCode("0000");
        response.setReturnMessage("交易成功");

        return response;
    }

    /**
     * 报文格式测试 处理
     *
     * @param request 请求
     * @return response
     */
    public GeshiTest2Resp geshiTest2(SecTest2BORequest request) {
        log.info("==================进入service==================");
        log.info("service接收的参数:{}", request);
        log.info("service获取参数name:{}", request.getBody().getName());
        log.info("service获取参数tranCode:{}", request.getHeader().getTranCode());
        // 业务处理

//        SecBOResponse response = new SecBOResponse();
//        SecBOBodyResp body = new SecBOBodyResp();
//        body.setReturnCode("0000");
//        body.setReturnMessage("交易成功");
//        response.setBody(body);
        // 不用在这里setHeader，直接在切面公共处理

        GeshiTest2Resp response = new GeshiTest2Resp();
        response.setReturnCode("0000");
        response.setReturnMessage("交易成功");

        return response;
    }
}
