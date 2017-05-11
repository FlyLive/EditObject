package Back.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ObjectDisplay {
    // 输出基本信息
    public ArrayList<Object> translateInfo(ResultSet rs, Class<?> clazz) {
        ArrayList<Object> result = new ArrayList<Object>();
        try {
            while (rs.next()) {
                Object obj = clazz.newInstance();
                for (Field field : clazz.getDeclaredFields()) {
                    String fieldName = field.getName();
                    String fieldValue = rs.getString(fieldName);
                    ObjectInput.setField(field, clazz, obj, fieldValue);
                }
                result.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public ArrayList<String> getAnnotations(Class<?> clazz) {

        ArrayList<String> annotations = new ArrayList<String>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            annotations.add("'" + ObjectInput.getPrompt(field) + "'");
        }
        return annotations;
    }

    public ArrayList<Object> searchAll(ResultSet rs, Class<?> clazz,String search) {
        ArrayList<Object> result = new ArrayList<Object>();
        try {
            while (rs.next()) {
                Object obj = clazz.newInstance();
                int index = -1;
                for (Field field : clazz.getDeclaredFields()) {
                    String fieldName = field.getName();
                    String fieldValue = rs.getString(fieldName);
                    ObjectInput.setField(field, clazz, obj, fieldValue);

                    if (fieldValue.contains(search)) {
                        index++;
                    }
                }
                if (index >= 0) {
                    result.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    // 获取所有属性
    public static Object[] getFields(Object obj, Class<?> clazz) {
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
            e.printStackTrace();
        }
        return values;
    }
}
