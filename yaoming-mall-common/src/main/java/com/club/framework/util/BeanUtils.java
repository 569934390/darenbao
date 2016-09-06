package com.club.framework.util;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.record.formula.functions.T;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean操作辅助类，如对象拷贝等
 */
public class BeanUtils {

    // 拷贝实例map
    private static Map<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<String, BeanCopier>();

    /**
     * 对象拷贝
     * 
     * @param source
     * @param target
     * @param overFlag ,true，覆盖原有值，false不覆盖，
     */
    public static void copyProperties(Object source, Object target,
            boolean overFlag) {
        // 组合主键
        String compKey = source.getClass().getName()
                + target.getClass().getName();
        BeanCopier copier = beanCopierMap.get(compKey);
        if (copier == null) {
            copier = BeanCopier.create(source.getClass(), target.getClass(),
                    !overFlag);
            beanCopierMap.put(compKey, copier);
            // synchronized (beanCopierMap) {
            // beanCopierMap.put(compKey, copier);
            // }
        }
        Converter converter = null;
        // 不覆盖拷贝
        if (!overFlag) {
            converter = new CustomConverter(target);
        }
        copier.copy(source, target, converter);
    }

    /**
     * 默认全量拷贝
     * 
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        copyProperties(source, target, true);
    }

    /**
     * 支持对象间值拷贝（不同类型则以属性名相同时映射）
     * 
     * @param orig
     * @param clazzT
     * @return
     */
    public static <T> T copy(Object orig, Class<T> clazzT) {
        try {
            T dest = clazzT.newInstance();

            if (orig == null) {
                return null;
            }

            PropertyDescriptor[] origDescriptors = PropertyUtils
                    .getPropertyDescriptors(orig);
            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue;
                }
                Object value = null;
                if (PropertyUtils.isReadable(orig, name)
                        && PropertyUtils.isWriteable(dest, name)) {
                    value = PropertyUtils.getSimpleProperty(orig, name);
                    PropertyUtils.setSimpleProperty(dest, name, value);
                }
            }
            return dest;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * List值拷贝
     * 
     * @param origList
     * @param clazzT
     * @return
     */
    public static <T> List<T> copy(List<?> origList, Class<T> clazzT) {
        try {
            List<T> destList = new ArrayList<T>();
            if (origList == null || origList.size() <= 0) {
                return null;
            }

            for (int i = 0; i < origList.size(); i++) {
                destList.add(copy(origList.get(i), clazzT));
            }
            return destList;

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param <T>
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws java.beans.IntrospectionException
     *             如果分析类属性失败
     * @throws IllegalAccessException
     *             如果实例化 JavaBean 失败
     * @throws InstantiationException
     *             如果实例化 JavaBean 失败
     * @throws java.lang.reflect.InvocationTargetException
     *             如果调用属性的 setter 方法失败
     */
    public static <T> T convertMap(Class<T> type, Map<String,Object> map){
        return JsonUtil.toBean(JsonUtil.toJson(map), type);
    }


    public static <T> T map2Bean(Map<String, Object> map, Class<T> class1) {
        T bean = null;
        try {
            bean = class1.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(bean, map);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static <T> List<T> convertList(Class<T> type, List<Map<String,Object>> list){
    	return JsonUtil.toList(JsonUtil.toJson(list), type);
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map<String,Object> convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class<? extends Object> type = bean.getClass();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
    public static Map<String,Object> convertBean(Object bean,Object nullField)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class<? extends Object> type = bean.getClass();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, nullField);
                }
            }
        }
        return returnMap;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    public static Map<String,Object> convertBeanNotNull(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class<? extends Object> type = bean.getClass();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                }
            }
        }
        return returnMap;
    }
}
