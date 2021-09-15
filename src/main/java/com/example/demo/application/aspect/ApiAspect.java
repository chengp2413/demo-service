package com.example.demo.application.aspect;

import com.example.demo.application.request.SecRequest;
import com.example.demo.application.request.SzybComRequest;
import com.example.demo.application.response.*;
import com.example.demo.utils.JsonUtils;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
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
        log.info("==================Api切面开始执行==================");
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        log.info("==================Api切面执行结束==================");
        log.info("==================交易执行时间：{}ms", System.currentTimeMillis() - startTime);
        return result;


    }
}


//log.info("==================Api切面开始执行==================");
//        long startTime = System.currentTimeMillis();
//        Object[] args = proceedingJoinPoint.getArgs();
//        Object result;
//        Class<?> aClass = args[0].getClass();
//        SecRequest request = null;
//
//        try {
//            if (args[0] instanceof SecRequest) {
//                request = (SecRequest) args[0];
//                String str1 = JsonUtils.jsonString(request.getHeader());
//                log.info("header的内容:{}", str1);
//                //解密data的内容
//                String str2 = "{\"name\":\"程鹏\"," + "\"age\":\"18\"}";
//                int m = str2.indexOf('{');
//                int n = str2.indexOf('}');
//                String str3 = str2.substring(m + 1, n);
//                log.info("str3:{}", str3);
//                String str4 = "{" +
//                        "\"header\":" + str1 + "," +
//                        str3 +
//                        "}";
//                log.info("请求报文的字符串:{}", str4);
//                Object obj1 = JsonUtils.jsonToObj(str4, aClass);
//                log.info("请求报文的对象:{}", obj1);
//                Object[] objects = new Object[1];
//                objects[0] = obj1;
//                result = proceedingJoinPoint.proceed(objects);
//
////                // 三方加密的请求
////                SecRequest request = (SecRequest) args[0];
////                log.info("请求header为：【{}】", request.getHeader());
////                log.info("请求body  为：【{}】", request.getBody());
////                // 解密data，并把明文参数拼成json，塞回body请求中
////                request.getBody().setData("{\n" +
////                        "        \"age\": \"23\",\n" +
////                        "        \"name\": \"程鹏\"\n" +
////                        "    }\n");
////                result = proceedingJoinPoint.proceed();
//
//                log.info("刚返回切面的结果为:{}", result);
//                String str5 = JsonUtils.jsonString(result);
//                log.info("转为String的结果:{}", str5);
//                //将str5字符串加密
//                String str6 = "abcdefg";
//
////                if (result instanceof SecBOResponse) {
////                    SecBOResponse secBOResponse = (SecBOResponse) result;
////                    SecResponse response = new SecResponse();
////                    // 入参和出参的header一样的，在这里公共处理就行，service只需要设置业务上的returnCode和returnMessage
////                    response.setHeader(JsonUtils.src2Obj(request.getHeader(), SecHeaderResp.class));
////                    // 加密code和message并放在body的data字段，然后全部塞回body
////                    String code = secBOResponse.getBody().getReturnCode();
////                    String message = secBOResponse.getBody().getReturnMessage();
////                    SecBodyResp secBodyResp = new SecBodyResp();
////                    secBodyResp.setData(code + message);
////                    secBodyResp.setSign(String.valueOf(System.currentTimeMillis()));
////                    response.setBody(secBodyResp);
////                    result = response;
////                }
//
//                GeshiTestResponse resp = new GeshiTestResponse();
//                ComHeaderResp headerResp = new ComHeaderResp();
//                SecBodyResp bodyResp = new SecBodyResp();
//                headerResp.setTranCode(request.getHeader().getTranCode());
//                headerResp.setVersionNo(request.getHeader().getVersionNo());
//                headerResp.setClientFlowNo(request.getHeader().getClientFlowNo());
//                bodyResp.setSign("abcdefg");
//                bodyResp.setData(str6);
//                resp.setHeader(headerResp);
//                resp.setBody(bodyResp);
//                result = resp;
//
//            } else {
//                // 其他api请求
//                result = proceedingJoinPoint.proceed();
//            }
//            log.info("请求结果为：{}", result);
//        } catch (Exception e) {
//            GeshiTestResponse resp = new GeshiTestResponse();
//            ComHeaderResp headerResp = new ComHeaderResp();
//            SecBodyResp bodyResp = new SecBodyResp();
//            headerResp.setTranCode(request.getHeader().getTranCode());
//            headerResp.setVersionNo(request.getHeader().getVersionNo());
//            headerResp.setClientFlowNo(request.getHeader().getClientFlowNo());
//            //这里怎么把错误码，错误信息组成{"returnCode":"9999","returnMessage":"错误信息"}字符串
//
//            ComResp comResp = new ComResp();
//            comResp.setReturnCode("9999");
//            comResp.setReturnCode("错误信息");
//            String str7 = JsonUtils.jsonString(comResp);
//            log.info("str7:{}", str7);
//            String str8 = "str7加密后的字符串";
//
//
//            bodyResp.setData(str8);
//            bodyResp.setSign(str8);
//            resp.setBody(bodyResp);
//            result = resp;
//        } finally {
//            long endTime = System.currentTimeMillis();
//            long count = endTime - startTime;
//            log.info("==================Api切面执行结束==================");
//            log.info("==================交易执行时间：{}ms", count);
//        }
//        return result;
//    }
