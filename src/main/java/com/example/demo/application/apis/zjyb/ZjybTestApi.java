package com.example.demo.application.apis.zjyb;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.example.demo.application.request.*;
import com.example.demo.application.response.ZjybA00101Resp;
import com.example.demo.common.util.zjyb.SM4Util;
import com.example.demo.common.util.zjyb.SM4Utils;
import com.example.demo.domain.service.response.GeshiTest2Resp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * 浙江医保测试api
 *
 * @author chengp
 */
@Slf4j
@Controller
@Validated
@RequestMapping

public class ZjybTestApi {

    @Autowired
    HttpServletResponse httpServletResponse;

    /**
     * api测试
     *
     * @return response
     */
    @PostMapping("/zjyb/apiTest")
//    @GetMapping("/zjyb/apiTest")
    public void apiTest(HttpServletResponse response) throws Exception {
        log.info("==================进入浙江医保Api==================");

//        log.info("参数：{}", request.getCanShu());

        int i = 0;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        while (i < 3) {
            response.getWriter().println("{\n" +
                    "    \"data\": {\n" +
                    "        \"test\": \"响应" + i + "\"\n" +
                    "    }\n" +
                    "}");
            response.getWriter().flush();
            i++;
            Thread.sleep(2000);
        }
        response.getWriter().close();

//        System.out.println(body);
//        System.out.println(request);
//        String s = request.toString();
//        String substring = s.substring(1, s.length() - 1);
//        System.out.println(substring);
//        log.info("api接收的参数:{}", request.getBody().getRecord().getSid());

//        ZjybA00101Resp response = new ZjybA00101Resp();
//
//        ZjybComHead respHead = new ZjybComHead();
//        respHead.setTxnDt("2021-10-28 14:42:03");
//        respHead.setMsgRef(request.getHead().getMsgRef());
//        respHead.setMsgId(request.getHead().getMsgId());
//        respHead.setTxCode(request.getHead().getTxCode());
//        respHead.setVersion(request.getHead().getVersion());
//        response.setHead(respHead);
//
//        ZjybA00101Resp.Body respBody = new ZjybA00101Resp.Body();
//        respBody.setCODE("0");
//        respBody.setTYPE("成功");
//        respBody.setMESSAGE("");
//        ZjybA00101Resp.DATA respData = new ZjybA00101Resp.DATA();
//        respData.setSM4(SM4Util.randomHexString(16));
//        respBody.setDATA(respData);
//        response.setBody(respBody);

        log.info("==================浙江医保Api处理结束==================");
    }
}
