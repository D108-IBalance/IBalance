package com.ssafy.ibalance.common.util;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class DtoConverter {

    private static final StringBuilder builder = new StringBuilder();

    public <T> T convertFromMap(LinkedHashMap<String, Object> resultMap, T convertObject) {
        try {
            Field[] declaredFields = convertObject.getClass().getDeclaredFields();

            for (Field field : declaredFields) {
                String fieldName = field.getName();
                String callerName = camelCaseToSnakeCase(fieldName);

                if(resultMap.containsKey(callerName)) {

                    Method setter = convertObject.getClass().
                            getMethod("set" + camelCaseToPascalCase(fieldName), field.getType());

                    if(field.getGenericType() instanceof ParameterizedType
                            && resultMap.get(callerName) instanceof List<?>) {
                        ParameterizedType parameterizedType = (ParameterizedType) (field.getGenericType());
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

                        List<LinkedHashMap<String, Object>> fieldList = (List<LinkedHashMap<String, Object>>) resultMap.get(callerName);
                        List<Object> convertedList = fieldList.stream()
                                .map(map -> convertFromMap(map, getConstructor(actualTypeArguments[0]))).toList();

                        setter.invoke(convertObject, convertedList);
                    } else if(resultMap.get(callerName) instanceof Serializable) {
                        setter.invoke(convertObject, resultMap.get(callerName));
                    } else {
                        LinkedHashMap<String, Object> convertedField = (LinkedHashMap<String, Object>) resultMap.get(callerName);
                        setter.invoke(convertObject, convertFromMap(convertedField, field.getType().getDeclaredConstructor().newInstance()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertObject;
    }

    private String camelCaseToPascalCase(String input) {
        builder.delete(0, builder.length());

        builder.append(Character.toUpperCase(input.charAt(0)));
        builder.append(input.substring(1));
        return builder.toString();
    }

    private static String camelCaseToSnakeCase(String input) {
        if(input == null || input.isEmpty()) {
            return input;
        }
        builder.delete(0, builder.length());

        char previousChar = input.charAt(0);
        builder.append(Character.toLowerCase(previousChar));

        for (int i = 1; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if(Character.isUpperCase(currentChar)) {
                builder.append('_').append(Character.toLowerCase(currentChar));
            } else {
                builder.append(currentChar);
            }
        }

        return builder.toString();
    }

    private Object getConstructor(Type type) {
        try {
            if(type instanceof Class<?> clazz) {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
