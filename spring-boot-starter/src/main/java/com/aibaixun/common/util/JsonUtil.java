package com.aibaixun.common.util;


import com.aibaixun.basic.exception.JsonParseException;
import com.aibaixun.basic.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 基于 Jackson 的 json 工具类
 * @author wangxiao
 */
public class JsonUtil {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    public static final byte[] EMPTY = new byte[0];

    static {
        // 忽略在json字符串中存在，但是在java对象中不存在对应属性的情况
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略空Bean转json的错误
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        // 允许不带引号的字段名称
        MAPPER.configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true);
        // 允许单引号
        MAPPER.configure(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(), true);
        // allow int startWith 0
        MAPPER.configure(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS.mappedFeature(), true);
        // 允许字符串存在转义字符：\r \n \t
        MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        // 排除空值字段
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.setTimeZone(TimeZone.getDefault());
    }

    /**
     * 对象转换为json字符串
     * @param o 要转换的对象
     */
    public static String toJSONString(Object o)  {
        return toJSONString(o, false);
    }

    /**
     * 对象转换为json字符串
     * @param o 要转换的对象
     * @param format 是否格式化json
     */
    public static String toJSONString(Object o, boolean format)  {
        try {
            if (o == null) {
                return "";
            }
            if (o instanceof Number) {
                return o.toString();
            }
            if (o instanceof String) {
                return (String)o;
            }
            if (format) {
                return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            }
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    /**
     * 字符串转换为指定对象
     * @param json json字符串
     * @param cls 目标对象
     */
    public static <T> T toObject(String json, Class<T> cls) {
        if(StringUtils.isBlank(json) || cls == null){
            return null;
        }
        try {
            return MAPPER.readValue(json, cls);
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    /**
     * 字符串转换为指定对象，并增加泛型转义
     * 例如：List<Integer> test = toObject(jsonStr, List.class, Integer.class);
     * @param json json字符串
     * @param parametrized 目标对象
     * @param parameterClasses 泛型对象
     */
    public static <T> T toObject(String json, Class<?> parametrized, Class<?>... parameterClasses)  {
        if(StringUtils.isBlank(json) || parametrized == null){
            return null;
        }
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses);
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    /**
     * 字符串转换为指定对象
     * @param json json字符串
     * @param typeReference 目标对象类型
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference)  {
        if(StringUtils.isBlank(json) || typeReference == null){
            return null;
        }
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    /**
     * 字符串转换为JsonNode对象
     * @param json json字符串
     */
    public static JsonNode parse(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    /**
     * 对象转换为map对象
     * @param o 要转换的对象
     */
    public static <K, V> Map<K, V> toMap(Object o)  {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return toObject((String)o, Map.class);
        }
        return MAPPER.convertValue(o, Map.class);
    }

    /**
     * json字符串转换为list对象
     * @param json json字符串
     */
    public static <T> List<T> toList(String json)  {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, List.class);
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    /**
     * json字符串转换为list对象，并指定元素类型
     * @param json json字符串
     * @param cls list的元素类型
     */
    public static <T> List<T> toList(String json, Class<T> cls)  {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, cls);
            return MAPPER.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e.getMessage());
        }
    }


    public static String toJson(Object obj)  {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static byte[] toJsonBytes(Object obj)  {
        try {
            String temp = MAPPER.writeValueAsString(obj);
            return StringUtil.isEmpty(temp) ? EMPTY : temp.getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static <T> T toObj(byte[] json, Class<T> cls)  {
        try {
            return toObj(new String(json, StandardCharsets.UTF_8), cls);
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static <T> T toObj(byte[] json, Type cls)  {
        try {
            return toObj(new String(json, StandardCharsets.UTF_8), cls);
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static <T> T toObj(InputStream inputStream, Class<T> cls)  {
        try {
            return MAPPER.readValue(inputStream, cls);
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static <T> T toObj(byte[] json, TypeReference<T> typeReference) {
        try {
            return toObj(new String(json, StandardCharsets.UTF_8), typeReference);
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static <T> T toJavaObj(Object fromValue, TypeReference<T> toValueTypeRef)  {
        try {
            return MAPPER.convertValue(fromValue, toValueTypeRef);
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static <T> T toObj(String json, Class<T> cls) {
        try {
            return MAPPER.readValue(json, cls);
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static <T> T toObj(String json, Type type) {
        try {
            return MAPPER.readValue(json, MAPPER.constructType(type));
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static <T> T toObj(String json, TypeReference<T> typeReference)  {
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static <T> T toObj(InputStream inputStream, Type type)  {
        try {
            return MAPPER.readValue(inputStream, MAPPER.constructType(type));
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static JsonNode toObj(String json)  {
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public static void registerSubtype(Class<?> clz, String type) {
        MAPPER.registerSubtypes(new NamedType[]{new NamedType(clz, type)});
    }

    public static ObjectNode createEmptyJsonNode() {
        return new ObjectNode(MAPPER.getNodeFactory());
    }

    public static ArrayNode createEmptyArrayNode() {
        return new ArrayNode(MAPPER.getNodeFactory());
    }

    public static JsonNode transferToJsonNode(Object obj) {
        return MAPPER.valueToTree(obj);
    }

    public static JavaType constructJavaType(Type type) {
        return MAPPER.constructType(type);
    }



    public static ObjectNode createObjNode () {
        return MAPPER.createObjectNode();
    }

    public static ArrayNode createArrayNode () {
        return MAPPER.createArrayNode();
    }

}
