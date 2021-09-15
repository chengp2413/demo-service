package com.example.demo.common.tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定长处理标签
 *
 * @author 程鹏 k6048
 * @date 2021/08/04
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedLength {

    /**
     * 固定长度
     *
     * @return 固定长度
     */
    int fixedLen();

    /**
     * 填充字符
     *
     * @return 填充字符
     */
    char fillChar();

    /**
     * 填充位置
     *
     * @return 填充位置
     */
    String fillSide();

    /**
     * 分隔符
     *
     * @return 分隔符
     */
    String cutSymbol() default "";
}
