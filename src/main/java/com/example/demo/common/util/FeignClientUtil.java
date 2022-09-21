package com.example.demo.common.util;

import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态创建Feign工具类
 *
 * @author chengp
 * @date 2022/02/16
 */
@Component
public class FeignClientUtil implements ApplicationContextAware {

    /**
     * applicationContext
     */
    private static ApplicationContext applicationContext = null;

    /**
     * 已创建的FeignClient
     */
    private static final Map<String, Object> BEAN_CACHE = new ConcurrentHashMap<>();

    /**
     * 设置ApplicationContext
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (FeignClientUtil.applicationContext == null) {
            FeignClientUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 获取对应Client包含URL
     *
     * @param serverName  serverName
     * @param targetClass targetClass
     * @param url         url
     * @param <T>         <T>
     * @return T
     */
    public static <T> T build(String serverName, Class<T> targetClass, String url) {
        return buildClient(serverName, targetClass, url);
    }

    /**
     * 获取对应Client
     *
     * @param serverName  serverName
     * @param targetClass targetClass
     * @param <T>         <T>
     * @return T
     */
    public static <T> T build(String serverName, Class<T> targetClass) {
        return buildClient(serverName, targetClass);
    }

    /**
     * 创建对应FeignClient
     *
     * @param serverName  serverName
     * @param targetClass targetClass
     * @param url         url
     * @param <T>         <T>
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    private static <T> T buildClient(String serverName, Class<T> targetClass, String url) {
        T t = (T) BEAN_CACHE.get(serverName);
        if (Objects.isNull(t)) {
            FeignClientBuilder.Builder<T> builder = new FeignClientBuilder(applicationContext).forType(targetClass, serverName).path(url);
            t = builder.build();
            BEAN_CACHE.put(serverName, t);
        }
        return t;
    }

    /**
     * 创建对应FeignClient
     *
     * @param serverName  serverName
     * @param targetClass targetClass
     * @param <T>         <T>
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    private static <T> T buildClient(String serverName, Class<T> targetClass) {
        T t = (T) BEAN_CACHE.get(serverName);
        if (Objects.isNull(t)) {
            FeignClientBuilder.Builder<T> builder = new FeignClientBuilder(applicationContext).forType(targetClass, serverName);
            t = builder.build();
            BEAN_CACHE.put(serverName, t);
        }
        return t;
    }
}