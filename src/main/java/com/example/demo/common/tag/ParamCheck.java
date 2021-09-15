package com.example.demo.common.tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法内字段判空
 *
 * @author 时良宝 k48299
 * @date 2021/6/25
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamCheck {

    /**
     * message
     *
     * @return 自定义信息
     */
    String message();
}
