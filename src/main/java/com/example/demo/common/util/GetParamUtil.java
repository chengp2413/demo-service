package com.example.demo.common.util;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.google.gson.Gson;

import java.util.Map;

/**
 * @author chengp
 * @version 1.0
 * @date 2022/4/27 10:13
 */
public class GetParamUtil {
    private GetParamUtil() {
    }

    public static String getApolloParam(String key) {
        Config appConfig = ConfigService.getAppConfig();
        String keyNamespace = appConfig.getProperty("chengp.pub.sysid-namespace", null);
        if (null == keyNamespace) {
            return null;
        }
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(keyNamespace, Map.class);
        String[] split = key.split("\\.");
        String namespace = map.get(split[1]);
        if (null == namespace) {
            return appConfig.getProperty(key, null);
        } else {
            return ConfigService.getConfig(namespace).getProperty(key, null);
        }
    }
}
