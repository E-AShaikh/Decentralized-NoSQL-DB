package com.db.util;

import com.db.model.query.DataType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JSONUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JSONObject generateJsonSchema(Class<?> classToInspect) {
//        try {
//            mapper.setVisibility(mapper.getSerializationConfig()
//                    .getDefaultVisibilityChecker()
//                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
//
//            JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
//            JsonSchema schema = schemaGen.generateSchema(classToInspect);
//
//            JSONObject castedSchema = new JSONObject(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
////            for (Object obj: (JSONObject) castedSchema.get("properties"))
//            return (JSONObject) castedSchema.get("properties");
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        Field[] fields = classToInspect.getDeclaredFields();

        JSONObject schema = new JSONObject();
        for (Field field : fields) {
            try {
                schema.put(field.getName(), DataType.valueOf(field.getType().getSimpleName().toUpperCase()));
            } catch (IllegalArgumentException e) {
                schema.put(field.getName(), "STRING");
            }
        }

        return schema;
    }

    public static <T> T parseObject(JSONObject content, Class<T> valueType) {
        return parseObject(content.toString(), valueType);
    }

    public static <T> T parseObject(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseJsonToList(JSONObject response, Class<T> clazz) {
//        JSONObject response = new JSONObject(jsonString);
        List<T> objects = new ArrayList<>();

        JSONArray jsonArr = (JSONArray) response.get("data");

        for (int i = 0; i < jsonArr.length(); i++) {
            T obj = mapJsonToObject( (JSONObject) jsonArr.get(i), clazz);
            objects.add(obj);
        }

        return objects;
    }

    public static <T> T mapJsonToObject(JSONObject jsonObject, Class<T> clazz) {
        T instance;
        try {
            instance = clazz.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (jsonObject.has(fieldName)) {
                    Object value = jsonObject.get(fieldName);
                    Class<?> fieldType = field.getType();
                    if (fieldType == double.class || fieldType == Double.class) {
                        if (value instanceof BigDecimal) {
                            field.set(instance, ((BigDecimal) value).doubleValue());
                        } else {
                            field.set(instance, value);
                        }
                    } else {
                        field.set(instance, value);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

}
