//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sixi.oaplatform.common.utils;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.commons.collections.MapUtils;
import org.dozer.DozerBeanMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtils {
    private static DozerBeanMapper map = new DozerBeanMapper();

    public BeanUtils() {
    }

    public static <T> T mapObject(Object source, Class<T> cls) {
        return source == null ? null : map.map(source, cls);
    }

    public static <T> T mapObject(Object source, Object target) {
        map.map(source, target);
        return (T) target;
    }

    public static <T> List<T> mapList(List<?> source, Class<T> cls) {
        List<T> listTarget = new ArrayList();
        if (source != null) {
            for (Object object : source) {
                T objTarget = mapObject(object, cls);
                listTarget.add(objTarget);
            }
        }
        return listTarget;
    }

    @SneakyThrows
    public static Map<String, Object> toMap(Object obj) {
        if (obj == null) {
            return MapUtils.EMPTY_MAP;
        } else {
            Map<String, Object> map = new HashMap();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object value = field.get(obj);
                map.put(field.getName(), value);
            }
            return map;
        }
    }

    @SneakyThrows
    public static Map<String, String> toStringMap(Object obj) {
        if (obj == null) {
            return MapUtils.EMPTY_MAP;
        } else {
            Map<String, String> map = new HashMap();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object value = field.get(obj);
                map.put(field.getName(), value == null ? null : value.toString());
            }
            return map;
        }
    }

    @SneakyThrows
    public static Map<String, String> toJsonMap(Object obj) {
        if (obj == null) {
            return MapUtils.EMPTY_MAP;
        } else {
            Map<String, String> map = new HashMap();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                Object value = field.get(obj);
                map.put(field.getName(), value == null ? null : JSON.toJSONString(value));
            }
            return map;
        }
    }
}