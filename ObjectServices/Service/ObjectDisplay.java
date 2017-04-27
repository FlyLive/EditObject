package Service;

import Annotations.Label;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectDisplay {
	// 输出基本信息
	public void displayInfo(ResultSet rs, Class<?> clazz) {
		try {
			while (rs.next()) {
				for (Field field : clazz.getDeclaredFields()) {
					Label col = (Label) field.getAnnotation(Label.class);
					String fieldName = field.getName();
					System.out.print(col.value() + ":"
							+ rs.getString(fieldName) + " ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 获取所有属性
	public Object[] getFields(Object obj, Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Method[] methods = clazz.getMethods();
		Object[] values = new Object[fields.length];

		try {
			for (int i = 0; i < fields.length; i++) {
				String setMethod = "get" + fields[i].getName();
				for (Method method : methods) {
					if (method.getName().toLowerCase()
							.equals(setMethod.toLowerCase())) {

						values[i] = method.invoke(obj);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values;
	}
}
