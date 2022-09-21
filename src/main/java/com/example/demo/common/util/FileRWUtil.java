package com.example.demo.common.util;

import com.example.demo.common.tag.TxtFields;
import com.example.demo.common.tag.TxtType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件读写工具类
 *
 * @author chengp
 * @version 1.0
 * @date 2022/8/27
 */
@Slf4j
public class FileRWUtil {

    /**
     * 私有化构造
     */
    private FileRWUtil() {
        //do nothing
    }

    /**
     * 文件写入
     *
     * @param filePath 文件全路径
     * @param fileObj  文件bean
     * @param append   是否续写
     * @param needHead 是否需要头分隔
     * @param needTail 是否需要尾分隔
     */
    public static void fileWrite(String filePath, Object fileObj, boolean append, boolean needHead, boolean needTail) {
        //不续写文件，做删除重建初始处理
        if (!append) {
            try {
                Files.delete(Paths.get(filePath));
            } catch (Exception e) {
                //do nothing
            }
            try {
                Files.createFile(Paths.get(filePath));
            } catch (Exception e) {
                //do nothing
            }
        }
        //判断文件是否存在
        if (!Files.exists(Paths.get(filePath))) {
            throw new IllegalStateException("文件不存在");
        }

        try {
            //获取文件实体类字段属性
            Field[] fields = fileObj.getClass().getDeclaredFields();
            //获取文件类型注解值
            TxtType txtType = fileObj.getClass().getAnnotation(TxtType.class);
            for (Field field : fields) {
                field.setAccessible(true);
                TxtFields txtFields = field.getAnnotation(TxtFields.class);
                //头数据处理
                if (txtFields.isHead()) {
                    Object headObj = field.get(fileObj);
                    beanToMsg(headObj, filePath, txtType.sep(), txtType.charCode(), needHead, needTail);
                }
                //明细数据处理
                if (!txtFields.isHead() && !txtFields.isTail()) {
                    if (null != field.get(fileObj)) {
                        List<?> array = (List<?>) field.get(fileObj);
                        for (Object detail : array) {
                            beanToMsg(detail, filePath, txtType.sep(), txtType.charCode(), needHead, needTail);
                        }
                    }
                }
                //尾数据处理
                if (txtFields.isTail()) {
                    Object tailObj = field.get(fileObj);
                    beanToMsg(tailObj, filePath, txtType.sep(), txtType.charCode(), needHead, needTail);
                }
            }
        } catch (Exception e) {
            log.error("文件写入运行异常");
            throw new IllegalStateException("文件写入运行异常");
        }

    }

    /**
     * 实体bean转换文件数据
     *
     * @param beanObj  实体bean
     * @param filePath 文件全路径
     * @param sep      分隔符
     * @param charCode 编码方式
     * @param needHead 是否有头分隔符
     * @param needTail 是否有尾分隔符
     */
    private static void beanToMsg(Object beanObj, String filePath, String sep, String charCode, boolean needHead, boolean needTail) {
        try {
            if (ObjectUtils.isEmpty(beanObj)) {
                return;
            }
            StringBuffer sb = new StringBuffer();
            Field[] headFields = beanObj.getClass().getDeclaredFields();
            for (Field headField : headFields) {
                headField.setAccessible(true);
                TxtFields txtFields = headField.getAnnotation(TxtFields.class);
                String tmpValue = (String) headField.get(beanObj);
                String fieldValue = tmpValue == null ? "" : new String(tmpValue.getBytes(charCode), charCode);
                if (-1 == txtFields.len()) {
                    sb.append(fieldValue).append(sep);
                } else {
                    String fixedMsg = setFixedMsg(fieldValue, txtFields.len(), txtFields.holder(), txtFields.from(), charCode);
                    sb.append(fixedMsg).append(sep);
                }
            }
            if (needHead) {
                sb.insert(0, sep);
            }
            if (!needTail) {
                sb.delete(sb.length() - sep.length(), sb.length());
            }
            sb.append("\n");
            Files.write(Paths.get(filePath), sb.toString().getBytes(charCode), StandardOpenOption.APPEND);
        } catch (IllegalAccessException e) {
            log.error("获取实体字段值异常");
            throw new IllegalStateException("获取实体字段值异常");
        } catch (UnsupportedEncodingException e) {
            log.error("编码格式错误");
            throw new IllegalStateException("编码格式错误");
        } catch (IOException e) {
            log.error("文件写入异常");
            throw new IllegalStateException("文件写入异常");
        } catch (Exception e) {
            log.error("实体转换文件数据运行异常");
            throw new IllegalStateException("实体转换文件数据运行异常");
        }
    }

    /**
     * 组定长字段
     *
     * @param fieldValue 字段值
     * @param len        定长长度
     * @param holder     占位符
     * @param from       占位方向
     * @param charCode   编码格式
     * @return String
     * @throws UnsupportedEncodingException
     */
    private static String setFixedMsg(String fieldValue, int len, byte holder, String from, String charCode) throws UnsupportedEncodingException {
        byte[] bytes = fieldValue.getBytes(charCode);
        byte[] destBytes = new byte[len];
        if (bytes.length > len) {
            throw new IllegalStateException("字段数据长度大于限定长度");
        } else {
            if ("R".equals(from)) {
                System.arraycopy(bytes, 0, destBytes, 0, bytes.length);
                for (int i = bytes.length; i < len; i++) {
                    destBytes[i] = holder;
                }
            } else if ("L".equals(from)) {
                System.arraycopy(bytes, 0, destBytes, len - bytes.length, bytes.length);
                for (int i = 0; i < len - bytes.length; i++) {
                    destBytes[i] = holder;
                }
            } else {
                throw new IllegalStateException("不支持的填充方向");
            }
        }
        return new String(destBytes, charCode);
    }


    /**
     * 文件读取
     *
     * @param filePath  文件全路径
     * @param fileClazz 文件实体类
     * @return <T>
     */
    public static <T> T fileRead(String filePath, Class<T> fileClazz) {
        try {
            //构造文件实体对象
            Object fileObj = fileClazz.newInstance();
            //获取文件实体类字段属性
            Field[] fields = fileClazz.getDeclaredFields();
            //获取文件类型注解值
            TxtType txtType = fileClazz.getAnnotation(TxtType.class);

            //获取文件结构属性
            Field head = null;
            Field tail = null;
            Field rows = null;
            for (Field field : fields) {
                field.setAccessible(true);
                TxtFields txtField = field.getAnnotation(TxtFields.class);
                if (txtField.isHead()) {
                    head = field;
                    continue;
                }
                if (txtField.isTail()) {
                    tail = field;
                    continue;
                }
                rows = field;
            }

            //读取文件
            List<String> fileLines = Files.readAllLines(Paths.get(filePath), Charset.forName(txtType.charCode()));
            if (CollectionUtils.isEmpty(fileLines)) {
                return null;
            }

            //文件头处理
            if (null != head) {
                String headMsg = fileLines.get(0);
                Object headObj = Class.forName(head.getGenericType().getTypeName()).newInstance();
                msgToBean(headMsg, txtType.sep(), headObj, txtType.charCode());
                head.set(fileObj, headObj);
                fileLines.remove(0);
            }

            //文件尾处理
            if (null != tail) {
                String tailMsg = fileLines.get(fileLines.size() - 1);
                Object tailObj = Class.forName(tail.getGenericType().getTypeName()).newInstance();
                msgToBean(tailMsg, txtType.sep(), tailObj, txtType.charCode());
                tail.set(fileObj, tailObj);
                fileLines.remove(fileLines.size() - 1);
            }

            //文件明细处理
            Class<?> clazz = getArrayClass(rows);
            List<Object> rowList = new ArrayList<>((int) (fileLines.size() * 1.5));
            for (String rowMsg : fileLines) {
                Object rowObj = Class.forName(clazz.getName()).newInstance();
                msgToBean(rowMsg, txtType.sep(), rowObj, txtType.charCode());
                rowList.add(rowObj);
            }
            rows.set(fileObj, rowList);
            return (T) fileObj;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("文件实体类异常");
            throw new IllegalStateException("文件实体类异常");
        } catch (IOException e) {
            log.error("文件不存在或字符集错误");
            throw new IllegalStateException("文件不存在或字符集错误");
        } catch (ClassNotFoundException e) {
            log.error("文件解析异常");
            throw new IllegalStateException("文件解析异常");
        } catch (Exception e) {
            log.error("文件读取运行异常");
            throw new IllegalStateException("文件读取运行异常");
        }
    }

    /**
     * 文件数据映射至实体bean
     *
     * @param lineMsg  文件数据
     * @param sep      分隔符
     * @param lineObj  实体bean
     * @param charCode 文件编码
     */
    private static void msgToBean(String lineMsg, String sep, Object lineObj, String charCode) {
        try {
            Field[] fields = getAllFields(lineObj.getClass());
            int begin = 0;
            String[] items = lineMsg.split(sep);
            for (int i = 0; i < fields.length; i++) {
                TxtFields txtFields = fields[i].getAnnotation(TxtFields.class);
                fields[i].setAccessible(true);
                if (-1 == txtFields.len()) {
                    fields[i].set(lineObj, items[i]);
                } else {
                    String msg = getFixedMsg(lineMsg, begin, txtFields.len(), txtFields.holder(), txtFields.from(), charCode);
                    fields[i].set(lineObj, msg);
                    begin = begin + txtFields.len();
                }
            }
        } catch (Exception e) {
            log.error("文件数据映射至实体异常");
            throw new IllegalStateException("文件数据映射至实体异常");
        }
    }

    /**
     * 获取定长数据有效信息
     *
     * @param lineMsg  定长数据
     * @param begin    开始下标
     * @param fixedLen 定长长度
     * @param holder   占位符
     * @param from     占位方向
     * @param charCode 字符编码
     * @return String
     * @throws UnsupportedEncodingException
     */
    private static String getFixedMsg(String lineMsg, int begin, int fixedLen, byte holder, String from, String charCode) throws UnsupportedEncodingException {
        byte[] bytes = lineMsg.getBytes(charCode);
        String fixedMsg = "";
        if ("R".equals(from)) {
            int m = 0;
            for (int i = begin; i < begin + fixedLen; i++) {
                if (holder != bytes[i]) {
                    m++;
                }
            }
            byte[] bytesField = new byte[m];
            System.arraycopy(bytes, begin, bytesField, 0, m);
            fixedMsg = new String(bytesField, charCode);
        } else if ("L".equals(from)) {
            for (int i = begin; i < begin + fixedLen; i++) {
                if (holder != bytes[i]) {
                    byte[] bytesField = new byte[begin + fixedLen - i];
                    System.arraycopy(bytes, i, bytesField, 0, begin + fixedLen - i);
                    fixedMsg = new String(bytesField, charCode);
                    break;
                }
            }
        } else {
            throw new IllegalStateException("不支持的填充方向");
        }
        return fixedMsg;
    }

    /**
     * 获取类的所有字段属性
     *
     * @param clazz 类
     * @return Field[]
     */
    private static Field[] getAllFields(Class<?> clazz) {
        Class<?> aClazz = clazz;
        List<Field> fieldList = new ArrayList<>();
        while (null != aClazz) {
            fieldList.addAll(0, Arrays.asList(aClazz.getDeclaredFields()));
            aClazz = aClazz.getSuperclass();
        }
        return fieldList.toArray(new Field[]{});
    }

    /**
     * 反推List内类名
     *
     * @param field List字段
     * @return Class<?>
     */
    private static Class<?> getArrayClass(Field field) {
        Type genericType = field.getGenericType();
        ParameterizedType pt = (ParameterizedType) genericType;
        return (Class<?>) pt.getActualTypeArguments()[0];
    }
}
