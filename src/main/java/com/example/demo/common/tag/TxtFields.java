package com.example.demo.common.tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文件字段注解 FileRWUtil
 *
 * @author chengp
 * @version 1.0
 * @date 2022/8/4 18:53
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TxtFields {

    /**
     * 字段名 必须与文件实体类中字段名相同
     *
     * @return String
     */
    String value();

    /**
     * 占位符
     *
     * @return byte
     */
    byte holder() default 0;

    /**
     * 字段长度 -1为不定长
     *
     * @return int
     */
    int len() default -1;

    /**
     * 填充方向
     *
     * @return String
     */
    String from() default "";

    /**
     * 是否文件头
     *
     * @return boolean
     */
    boolean isHead() default false;

    /**
     * 是否文件尾
     *
     * @return boolean
     */
    boolean isTail() default false;
}
