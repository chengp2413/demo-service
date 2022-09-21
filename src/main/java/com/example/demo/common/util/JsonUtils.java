package com.example.demo.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author 单红宇
 */
public class JsonUtils {

    private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        // 对于空的对象转json的时候不抛出错误
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 允许属性名称没有引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 设置输入时忽略在json字符串中存在但在java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 设置输出时包含属性的风格
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 无参的私有构造方法
     */
    private JsonUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * JSON字符串转换为实体类
     *
     * @param jsonStr
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T jsonToObj(String jsonStr, Class<T> clz) {
        return jsonToBean(jsonStr, clz);
    }

    /**
     * 原实体类转换为目标实体类
     *
     * @param src
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T src2Obj(Object src, Class<T> clz) {
        String s = jsonString(src);
        if (s == null)
            return null;
        return jsonToBean(s, clz);
    }

    /**
     * 原实体类List转换为目标实体类List
     *
     * @param srcList
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> List<T> srcList2ObjList(List<?> srcList, Class<T> clz) {
        String s = jsonString(srcList);
        if (s == null)
            return new ArrayList<>();
        return jsonToList(s, clz);
    }

    /**
     * 将对象转成json格式
     *
     * @param object
     * @return String
     */
    public static String toJSONString(Object object) {
        return jsonString(object);
    }

    /**
     * 将对象转成json格式
     *
     * @param data
     * @return String
     */
    public static String jsonString(Object data) {
        if (data == null) {
            return null;
        }

        String json = null;
        try {
            json = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            logger.error("[{}] toJsonString error：{}", data.getClass().getSimpleName(), e);
        }
        return json;
    }

    /**
     * 将json转成特定的cls的对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> cls) {
        T t = null;
        try {
            t = mapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            outJsonProcessingExceptionLog(json, cls.getSimpleName(), e);
        }
        return t;
    }

    private static void outJsonProcessingExceptionLog(String json, String clsName, JsonProcessingException e) {
        logger.error(" parse json [{}] to class [{}]", json, clsName, e);
    }

    /**
     * json字符串转成list
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> cls) {
        try {
            return mapper.readValue(jsonString, getCollectionType(ArrayList.class, cls));
        } catch (JsonProcessingException e) {
            outJsonProcessingExceptionLog(jsonString, cls.getSimpleName(), e);
        }
        return new ArrayList<>();
    }

    /**
     * json字符串转成Map<String, T>
     *
     * @param jsonString
     * @param mapValueCls
     * @return
     */
    public static <T> Map<String, T> jsonToMapBean(String jsonString, Class<T> mapValueCls) {
        try {
            return mapper.readValue(jsonString, getCollectionType(HashMap.class, String.class, mapValueCls));
        } catch (JsonProcessingException e) {
            outJsonProcessingExceptionLog(jsonString, mapValueCls.getSimpleName(), e);
        }
        return null;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  实体bean
     * @return JavaType Java类型
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * json字符串转成list中有map的
     *
     * @param jsonString
     * @return
     */
    public static <T> List<Map<String, T>> jsonToListMaps(String jsonString) {
        CollectionLikeType type = mapper.getTypeFactory().constructCollectionLikeType(ArrayList.class, HashMap.class);
        try {
            return mapper.readValue(jsonString, type);
        } catch (JsonProcessingException e) {
            logger.error(" parse json [{}] to [List<Map>]", jsonString, e);
        }
        return new ArrayList<>();
    }

    /**
     * json字符串转成map的
     *
     * @param jsonString
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> jsonToMaps(String jsonString) {
        try {
            return mapper.readValue(jsonString, getCollectionType(HashMap.class, String.class,
                    Object.class));
        } catch (JsonProcessingException e) {
            logger.error(" parse json [{}] to [List<Map>]", jsonString, e);
        }
        return new HashMap<>();
    }
}
