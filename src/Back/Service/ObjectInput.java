package Back.Service;

import Annotations.Label;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class ObjectInput {
	// 设置属性
	public static void setField(Field field, Class<?> clazz, Object obj,String input)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method[] methods = clazz.getMethods();
		String setMethod = "set" + field.getName();
		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(setMethod)) {
				Object args = getCheckedInput(field.getType().getSimpleName(),input);

				method.invoke(obj, args);
			}
		}
	}

	// 获取注解的提示
	public static String getPrompt(Field field) {
		Label fieldLabel = field.getAnnotation(Label.class);
		return fieldLabel.value();
	}

	// 获取经过校验的输入
	public static Object getCheckedInput(String type,String input) {
		Object obj = null;
		try {
			if(type.equalsIgnoreCase("int")){
				obj = Integer.parseInt(input);
			}else if (type.equalsIgnoreCase("String")) {
				obj = input;
			} else if (type.equalsIgnoreCase("Integer")) {
				obj = Integer.valueOf(input);
			} else if (type.equalsIgnoreCase("Boolean")) {
				obj = input.equalsIgnoreCase("1") ? true : false;
			} else if (type.equalsIgnoreCase("double")) {
				obj = Double.valueOf(input);
			}
		} catch (Exception e) {
			return null;
		}
		return obj;
	}
}
