package com.example.demo.common.tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段判空标签
 *
 * @author chengp
 * @date 2022/05/17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamCheck {

    /**
     * message
     *
     * @return 自定义返回信息
     */
    String message();
}
