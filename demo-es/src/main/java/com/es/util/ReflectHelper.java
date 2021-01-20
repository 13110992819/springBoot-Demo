package com.es.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 反射帮助类
 * @version v1.0
 * @date 2019/2/21 16:43
 * @since v1.0
 */
public class ReflectHelper {

    private static  final Logger LOG = LoggerFactory.getLogger(ReflectHelper.class);

    /**
     *反射获取某个字段的数据
     * @param obj  实体数据
     * @param key  字段
     * @return  字段对应的值
     */
    public static Object valueGet(Object obj, String key){
       return valueGet(obj,key,false);
    }

    /**
     *反射获取某个字段的数据
     * @param obj  实体数据
     * @param key  字段
     * @param includeParent 是否包含父类字段
     * @return 字段对应的值
     */
    public static Object valueGet(Object obj, String key, boolean includeParent){
        if (obj == null){
            return null;
        }
        try {
            Field[] fields ;
            //获取包含父类的字段
            if(includeParent){
                Class<?> clazz = obj.getClass();
                List<Field> fieldList = new ArrayList<>();
                while (clazz != null){
                    fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
                    clazz = clazz.getSuperclass();
                }
                fields = new Field[fieldList.size()];
                fieldList.toArray(fields);
            }else {
                //只获取当前类的字段
                fields = obj.getClass().getDeclaredFields();
            }
            for (Field field : fields) {
                // 修改访问权限
                field.setAccessible(true);
                if (key.equals(field.getName())) {
                    return field.get(obj);
                }
            }
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException e) {
            LOG.error("ReflectHelper.valueGet出错", e);
    }
        return null;
    }

    /**
     *  对应有参数的方法  比如entity的set方法setAttr1(value)
     * @param object
     * @param key 传方法名 如“setAttr1” “setAttr2”
     * @param value 方法key的参数(Object)
     * @return
     * @author zheng.li
     * @time 2017-4-19 上午10:57:52
     * 修改时间：2018-5-17 by zcp 在原有的value.getClass()上增加类型判断.因为像ArrayList这种，getDeclaredMethod方法只识别到它的父级接口的class，即List.class
     */
    public static boolean valueSet(Object object, String key, Object value) {
        try {
            Method method = null;
            Class parameterTypes = value.getClass();
            if (parameterTypes.equals(HashSet.class)) {
                parameterTypes = Set.class;
            }else if (parameterTypes.equals(ArrayList.class) || parameterTypes.equals(LinkedList.class)) {
                parameterTypes = List.class;
            }else if (parameterTypes.equals(HashMap.class) || parameterTypes.equals(LinkedHashMap.class)) {
                parameterTypes = Map.class;
            }
            method = object.getClass().getDeclaredMethod(JavaBeansUtil.getSetterMethodName(key),
                    parameterTypes);
            // 因为写成private 所以这里必须设置
            method.setAccessible(true);
            method.invoke(object, value);
            return true;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOG.error("ReflectHelper.valueSet 出错", e);
        }
        return false;
    }

    /**
     * 判断是否存在名为key的成员变量
     * @param obj
     * @param key 传属性名 如“attr1” “attr2”
     * @return true or false
     */
    public static boolean exist(Object obj, String key){
        if (obj == null) {
            return false;
        }
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            Field field = null;
            for (int i = 0; i < fields.length; i++) {
                field = fields[i];
                field.setAccessible(true);// 修改访问权限
                if (key.equals(field.getName())) {
                    return true;
                }
            }

        } catch (SecurityException | IllegalArgumentException e) {
            LOG.error("ReflectHelper.exist 出错", e);
        }

        return false;
    }

    /**
     * 检查对象内的每个字段是否为空
     * @param object  对象
     * @return    空true or   非空false
     * @throws IllegalAccessException  异常
     */
    public static boolean checkObjAllFieldsIsNull(Object object) throws IllegalAccessException {
        if (null == object) {
            return true;
        }
        for (Field f : object.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(object) == null || StringUtil.isEmpty(f.get(object).toString())) {
                return true;
            }
        }
        return false;
    }
}
