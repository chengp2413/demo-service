package com.example.demo.infrastructure.external.feign;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 外部接出
 *
 * @author chengp
 * @date 2022/01/30
 */
public interface ComExtClient {

    /**
     * 外部接出
     *
     * @param serviceName 微服务名
     * @param uri         请求URI
     * @param request     请求报文
     * @return Object
     */
    @PostMapping(value = "{uri}", headers = {"Content-Type: application/json"})
    Object deal(@RequestHeader("NEBULA-DST-SERVICE") String serviceName,
                @PathVariable(value = "uri") String uri, @RequestBody Object request);
}
