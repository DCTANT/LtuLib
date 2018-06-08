package com.tstdct.lib;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CS002 on 2016/11/7.
 */

public class MapUtil {
	public static Map<String, Object> getMap(Object object) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			final Class<?> classObj = object.getClass();
			Field[] superFields = classObj.getSuperclass().getDeclaredFields();
			for (Field field : superFields) {
				if (!field.isAccessible()){
					field.setAccessible(true);
				}
				String key = field.getName();
				Object value = field.get(object);
				System.out.println(key+":"+value);
				if (value != null){
					map.put(key,value);
				}
			}
			Field[] fields = classObj.getDeclaredFields();
			for (Field field : fields) {
				if (!field.isAccessible()){
					field.setAccessible(true);
				}
				String key = field.getName();
				Object value = field.get(object);
				System.out.println(key+":"+value);
				if (value != null){
					if (value instanceof List){
						map.put(key,(List)value);
					}else {
						map.put(key, value);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return map;
	}
}
