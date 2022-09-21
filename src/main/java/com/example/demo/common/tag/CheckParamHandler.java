package com.example.demo.common.tag;

import cn.hutool.core.bean.BeanUtil;
import com.example.demo.common.exception.TransException;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 字段判空标签处理方法
 *
 * @author chengp
 * @date 2022/05/17
 */
@Slf4j
public class CheckParamHandler {
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
    public CheckParamHandler(Object object) {
        this.object = object;
        this.clazz = object.getClass();
        this.fields = object.getClass().getDeclaredFields();
    }

    /**
     * 参数判空
     */
    public void checkParam() {
        for (Field field : fields) {
            checkField(field);
        }
    }

    /**
     * 父类参数判空
     */
    public void checkSuperParam() {
        for (Field field : object.getClass().getSuperclass().getDeclaredFields()) {
            checkField(field);
        }
    }

    /**
     * 字段判空
     *
     * @param field 字段属性
     */
    private void checkField(Field field) {
        ParamCheck paramCheck = field.getAnnotation(ParamCheck.class);
        if (null == paramCheck) {
            return;
        }
        try {
            String message = paramCheck.message();
            String fieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, field.getName());
            String methodName = "get" + fieldName;
            Method method = clazz.getMethod(methodName);
            Object invoke = method.invoke(object);
            if (BeanUtil.isEmpty(invoke)) {
                throw new TransException("9999", message);
            }
        } catch (InvocationTargetException e) {
            log.error("CheckParamHandler InvocationTargetException", e);
        } catch (IllegalAccessException e) {
            log.error("CheckParamHandler IllegalAccessException", e);
        } catch (NoSuchMethodException e) {
            log.error("CheckParamHandler NoSuchMethodException", e);
        }
    }

}
