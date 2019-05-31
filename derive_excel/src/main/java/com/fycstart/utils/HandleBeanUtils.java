package com.fycstart.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/22下午 2:41
 */
public class HandleBeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(ListUtils.class);

    /**
     * 转换实体为list
     *
     * @param entity
     * @param <F>
     * @return
     */
    public static <F> List<Object> entityToList(F entity) {
        if (entity == null) {
            return null;
        }
        List<Object> returnList = null;
        logger.debug("entityToModel : Entity属性的值转换为List");
        Class<?> aClass = entity.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        if (declaredFields.length > 0) {
            returnList = Arrays.stream(declaredFields).map(field -> {
                Object invoke = null;
                try {
                    Method method = aClass.getMethod("get" + getMethodName(field.getName()));
                    //值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查
                    method.setAccessible(Boolean.TRUE);
                    invoke = method.invoke(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return invoke;
            }).collect(Collectors.toList());
        }

        return returnList;
    }


    /**
     * 把实体类的属性名转换为List集合
     *
     * @param c
     * @return
     */
    public static List<String> entityAttributeNamesToList(Class c) {

        if (c == null) {
            return null;
        }
        logger.debug("entityToModel : Entity属性名转换为List");
        Field[] declaredFields = c.getDeclaredFields();
        if (declaredFields.length > 0) {

            List<String> collect = Arrays.stream(declaredFields).map(field -> {

                return field.getName();
            }).collect(Collectors.toList());
            return collect;
        }

        return null;
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

}
