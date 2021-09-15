package com.example.demo.common.tag;

import com.example.demo.common.exception.TransException;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 判空标签配合方法
 *
 * @author 时良宝 k48299
 * @date 2021/6/25
 */
@Slf4j
public class CheckPointParamHandler {
    /**
     * 类
     */
    private Class clazz;
    /**
     * 字段
     */
    private Field[] fields;
    /**
     * 传入实体对象
     */
    private Object object;

    /**
     * 构造方法
     *
     * @param object 实体
     */
    public CheckPointParamHandler(Object object) {
        this.object = object;
        this.clazz = object.getClass();
        this.fields = object.getClass().getDeclaredFields();
    }

    /**
     * 字段判空
     */
    public void getParamCheckResult() {
        for (Field field : fields) {
            Annotation annotation = null;
            annotation = field.getAnnotation(ParamCheck.class);
            if (annotation == null) {
                continue;
            }
            try {
                String message = ((ParamCheck) annotation).message();
                String name = field.getName();
                String szm = name.substring(0, 1).toUpperCase();
                name = name.substring(1);
                String tempStr = "get" + szm + name;
                Method method = clazz.getMethod(tempStr);
                Object invoke = method.invoke(object);
                if (null == invoke || "".equals(invoke)) {
                    throw new TransException("9999", message);
                }
            } catch (InvocationTargetException e) {
                log.error("自定义判空映射异常：{}", e);
            } catch (IllegalAccessException e) {
                log.error("自定义判空存取异常：{}", e);
            } catch (NoSuchMethodException e) {
                log.error("自定义判空获取方法异常：{}", e);
            }
        }
    }

}
