package com.example.demo.common.tag;

import com.example.demo.common.constant.Constants;
import com.example.demo.common.exception.TransException;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 定长标签配合处理方法
 *
 * @author 程鹏 k6048
 * @date 2021/08/04
 */
@Slf4j
public class FixedLengthHandler {

    /**
     * 实体对象类
     */
    private Class clazz;

    /**
     * 实体对象属性集合
     */
    private Field[] fields;

    /**
     * 实体对象
     */
    private Object object;

    /**
     * 构造方法
     *
     * @param object 实体对象
     */
    public FixedLengthHandler(Object object) {
        this.object = object;
        this.clazz = object.getClass();
        this.fields = object.getClass().getDeclaredFields();
    }


    /**
     * 组包（实体对象组装定长字符串）
     *
     * @param charsetName 编码方式
     * @return 定长字符串
     */
    public String assambleFixedLenStr(String charsetName) {
        StringBuilder fixedLenStr = new StringBuilder();
        try {
            for (Field field : fields) {
                FixedLength annotation = field.getAnnotation(FixedLength.class);
                if (null != annotation) {
                    int fixedLen = annotation.fixedLen();
                    char fillChar = annotation.fillChar();
                    String fillSide = annotation.fillSide();
                    String cutSymbol = annotation.cutSymbol();
                    String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method method = clazz.getMethod("get" + fieldName);
                    String fieldValue = (String) method.invoke(object);
                    String handleFieldValue = this.fieldFill(fieldValue, fixedLen, fillChar, fillSide, charsetName);
                    if ("".equals(cutSymbol)) {
                        fixedLenStr.append(handleFieldValue);
                    } else {
                        fixedLenStr.append(handleFieldValue).append(cutSymbol);
                    }
                }
            }
        } catch (TransException e) {
            log.error("定长标签组包异常：{}", e.getErrorMessage());
            return null;
        } catch (Exception e) {
            log.error("定长标签组包异常：{}", (Object) e.getStackTrace());
            return null;
        }
        return fixedLenStr.toString();
    }

    /**
     * 解包（定长字符串解析至实体对象）
     *
     * @param fixedLenStr 定长字符串
     * @param charsetName 编码方式
     * @return 解包结果
     */
    public boolean analyseFixedLenStr(String fixedLenStr, String charsetName) {
        int begin = 0;
        try {
            for (Field field : fields) {
                FixedLength annotation = field.getAnnotation(FixedLength.class);
                if (null != annotation) {
                    int fixedLen = annotation.fixedLen();
                    char fillChar = annotation.fillChar();
                    String fillSide = annotation.fillSide();
                    String cutSymbol = annotation.cutSymbol();
                    String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    String fieldValue = null;
                    if ("".equals(cutSymbol)) {
                        fieldValue = this.getRealStr(fixedLenStr, begin, fixedLen, fillChar, fillSide, charsetName);
                    } else {
                        fieldValue = this.getRealStr(fixedLenStr.replace(cutSymbol, ""),
                                begin, fixedLen, fillChar, fillSide, charsetName);
                    }
                    Method setSrc = clazz.getDeclaredMethod("set" + fieldName, field.getType());
                    Object obj = fieldValue;
                    setSrc.invoke(object, obj);
                    begin = begin + fixedLen;
                }
            }
            return true;
        } catch (TransException e) {
            log.error("定长标签解包异常：{}", e.getErrorMessage());
            return false;
        } catch (Exception e) {
            log.error("定长标签解包异常：{}", (Object) e.getStackTrace());
            return false;
        }
    }


    /**
     * 字段填充
     *
     * @param formStr     原字段字符串
     * @param fixedLen    固定长度
     * @param fillChar    填充字符
     * @param fillSide    填充位置
     * @param charsetName 编码方式
     * @return 填充后字符串
     * @throws UnsupportedEncodingException 读取字节异常
     */
    private String fieldFill(String formStr, int fixedLen, char fillChar, String fillSide, String charsetName)
            throws UnsupportedEncodingException {
        String formStr1 = formStr == null ? "" : formStr;
        StringBuilder builder = new StringBuilder();
        byte[] bytes = formStr1.getBytes(charsetName);
        if (bytes.length < fixedLen) {
            builder.append(formStr1);
            if (Constants.RIGHT.equals(fillSide)) {
                for (int i = bytes.length; i < fixedLen; i++) {
                    builder.append(fillChar);
                }
            } else if (Constants.LEFT.equals(fillSide)) {
                for (int i = bytes.length; i < fixedLen; i++) {
                    builder.insert(0, fillChar);
                }
            } else {
                throw new TransException("9999", "不支持的填充方式");
            }
        } else {
            builder.append(formStr1);
        }
        return builder.toString();
    }


    /**
     * 获取对应位置真实值('0'填充符只允许左填充)
     *
     * @param fixedLenStr 定长字符串
     * @param begin       开始下标
     * @param fixedLen    固定长度
     * @param fillChar    填充字符
     * @param fillSide    填充位置
     * @param charsetName 编码方式
     * @return 真实字符串
     * @throws UnsupportedEncodingException 读取字节异常
     */
    private String getRealStr(String fixedLenStr, int begin, int fixedLen, char fillChar, String fillSide,
                              String charsetName) throws UnsupportedEncodingException {
        byte[] bytes = fixedLenStr.getBytes(charsetName);
        byte[] bytesField = new byte[fixedLen];
        if (Constants.RIGHT.equals(fillSide)) {
            int m = 0;
            for (int i = begin; i < begin + fixedLen; i++) {
                if (fillChar != bytes[i]) {
                    m++;
                }
            }
            System.arraycopy(bytes, begin, bytesField, 0, m);
        } else if (Constants.LEFT.equals(fillSide)) {
            for (int i = begin; i < begin + fixedLen; i++) {
                if (fillChar != bytes[i]) {
                    System.arraycopy(bytes, i, bytesField, 0, begin + fixedLen - i);
                    break;
                }
            }
        } else {
            throw new TransException("9999", "不支持的填充方式");
        }
        return new String(bytesField, charsetName);
    }
}
