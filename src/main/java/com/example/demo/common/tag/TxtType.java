package com.example.demo.common.tag;

import java.lang.annotation.*;

/**
 * 文件类型注解 FileRWUtil
 *
 * @author chengp
 * @version 1.0
 * @date 2022/8/4 18:47
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TxtType {

    /**
     * 字符编码
     *
     * @return String
     */
    String charCode();

    /**
     * 分隔符
     *
     * @return String
     */
    String sep();
}
