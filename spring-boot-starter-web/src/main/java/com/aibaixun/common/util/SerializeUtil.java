package com.aibaixun.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class SerializeUtil {

    private static ObjectMapper objectMapper = null;

    public SerializeUtil() {
    }

    public static ObjectMapper get() {
        if (objectMapper == null) {
            objectMapper = createObjectMapper();
        }

        return objectMapper;
    }

    private static ObjectMapper createObjectMapper(SimpleDateFormat des, SimpleDateFormat ser) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setTimeZone(TimeZone.getDefault());
        return objectMapper;
    }

    private static ObjectMapper createObjectMapper() {
        SimpleDateFormat dateTimeSdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return createObjectMapper(dateTimeSdf2, dateTimeSdf2);
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return get().getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static void writeResponse(HttpServletResponse response, Object data) {
        response.setContentType("application/json;charset=UTF-8");

        try {
            Writer writer = response.getWriter();
            writer.write(get().writeValueAsString(data));
            writer.flush();
            writer.close();
        } catch (IOException var3) {
        }

    }
}
