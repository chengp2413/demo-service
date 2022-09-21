package com.example.demo.common.bsnconfig;

import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chengp
 * @version 1.0
 * @date 2022/5/2 10:37
 */
@Data
@Component
public class DemoConfig {

    @Value("${chengp.pub.namespaces}")
    private String namespaces;

    @ApolloJsonValue("${chengp.pub.sysid-namespace}")
    private Map<String, String> map;
}
