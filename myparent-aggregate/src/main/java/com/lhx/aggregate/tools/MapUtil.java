package com.lhx.aggregate.tools;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Map工具类
 * 
 */
public class MapUtil {
	private static final Logger log = LoggerFactory.getLogger(MapUtil.class);

	/**
	 * 把map转换成key为string的map(freemarker可以访问)
	 * 
	 * @param objMap
	 * @return
	 * @since 2015年12月23日 下午1:42:45
	 */
	public static Map<String, Object> toStringKeyMap(Map<?, ?> objMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null == objMap || 0 == objMap.size()) {
			log.info("转换对象的objMap为null或者size为0!");
			return result;
		}
		for (Entry<?, ?> entry : objMap.entrySet()) {
			String key = String.valueOf(entry.getKey());
			Object value = entry.getValue();
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 把list转换成key为string的map(freemarker可以访问)
	 * 
	 * @param objList
	 * @return
	 * @since 2016年1月4日 下午3:00:01
	 */
	public static Map<String, Object> toStringKeyMap(List<?> objList,
			String keyColumn) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null == objList || 0 == objList.size()) {
			log.info("转换对象的objMap为null或者size为0!");
			return result;
		}
		if (null == keyColumn) {
			log.info("没有指定为String的key!");
			return result;
		}
		try {
			for (Object object : objList) {
				Field field = ReflectionUtils.findField(object.getClass(),
						keyColumn);
				if (null == field) {
					log.info("List元素中没有字段:" + keyColumn);
					break;
				}
				field.setAccessible(true);
				String key = field.get(object).toString();// 获取该字段的String值
				result.put(key, object);
			}
		} catch (Exception e) {
			log.error("List转换成Map异常!", e);
		}
		return result;
	}

	/**
	 * 把json字符串转成map
	 * 
	 * @param str
	 * @return
	 * @since 2016年1月8日 下午5:21:13
	 */
	public static Map<String, Object> toMapObj(String str) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (null == str || str.trim().length() == 0) {
			return result;
		}
		JSONObject json = JSONObject.parseObject(str);
		if (null == json || json.isEmpty()) {
			return result;
		}
		for (String key : json.keySet()) {
			result.put(key, json.get(key));
		}
		return result;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws java.beans.IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws java.lang.reflect.InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Map<String, Object> convertBean(Object bean) {
		Class<? extends Object> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<>();
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
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
		} catch (Exception e) {
			log.error("Object转换成Map异常!", e);
		}
		return returnMap;
	}

	public static JSONObject toJson(Map<String, Object> map) {
		JSONObject json = new JSONObject();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			json.put(key, map.get(key));
		}
		return json;
	}

}
