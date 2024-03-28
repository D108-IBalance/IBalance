package com.ssafy.ibalance.common.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

@Component
public class DtoConverter {

    private static final StringBuilder builder = new StringBuilder();

    public <T> T convertFromMap(LinkedHashMap<String, String> resultMap, T convertObject) {
        try {
            Field[] declaredFields = convertObject.getClass().getDeclaredFields();

            for (Field field : declaredFields) {
                String fieldName = field.getName();

                if(resultMap.containsKey(fieldName)) {
                    Method setter = convertObject.getClass().getMethod("set" + camelCaseToPascalCase(fieldName), field.getType());
                    setter.invoke(convertObject, resultMap.get(fieldName));
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
}
