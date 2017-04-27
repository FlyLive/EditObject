package Service;

import Annotations.Label;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class ObjectInput {
	// 通过映射的类及注解创建对象
	public Object createObject(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Object object = null;

		try {
			object = clazz.newInstance();
			for (int i = 0; i < fields.length; i++) {
				setField(fields[i], clazz, object);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	// 设置属性
	public void setField(Field field, Class<?> clazz, Object obj)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method[] methods = clazz.getMethods();
		String setMethod = "set" + field.getName();
		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(setMethod)) {
				getPrompt(field);

				Object args = getCheckedInput(field.getType().getSimpleName());

				method.invoke(obj, args);
			}
		}
	}

	// 通过映射的类及注解修改对象
	public Object modifyObject(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Object obj = null;

		try {
			obj = clazz.newInstance();
			for (int i = 0; i < fields.length; i++) {
				if (!fields[i].getName().equals("idNo")) {
					setField(fields[i], clazz, obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	// 获取注解的提示
	public void getPrompt(Field field) {
		Label fieldLabel = field.getAnnotation(Label.class);
		System.out.println(fieldLabel.value());
	}

	// 获取经过校验的输入
	public Object getCheckedInput(String type) {
		Scanner sc = new Scanner(System.in);
		Object input = null;
		try {
			if(type.equalsIgnoreCase("int")){
				input = sc.nextInt();
			}else if (type.equalsIgnoreCase("String")) {
				input = sc.nextLine();
			} else if (type.equalsIgnoreCase("Integer")) {
				input = Integer.valueOf(sc.nextLine());
			} else if (type.equalsIgnoreCase("Boolean")) {
				String temp = sc.nextLine();
				if (!temp.equals("true") && !temp.equals("false")) {
					System.out.println("类型错误,请重试");
					input = getCheckedInput(type);
				} else {
					input = Boolean.valueOf(temp);
				}
			} else if (type.equalsIgnoreCase("double")) {
				input = Double.valueOf(sc.nextLine());
			}
		} catch (Exception e) {
			System.out.println("类型错误,请重试");
			input = getCheckedInput(type);
		}
		return input;
	}
}
