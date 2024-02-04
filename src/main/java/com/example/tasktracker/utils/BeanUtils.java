package com.example.tasktracker.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class BeanUtils {

    @SneakyThrows
    public static void nonNullPropertiesCopy(Object from, Object to) {
        Field[] fields = from.getClass().getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            Object source = field.get(from);
            if(source == null) continue;
            field.set(to, source);
        }
    }
}
