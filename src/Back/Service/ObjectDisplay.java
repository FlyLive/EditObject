package Back.Service;

import Annotations.Label;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
                    ObjectInput.setField(field,clazz,obj,rs.getString(fieldName));
                }
                result.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
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

    public ArrayList<String> getAnnotations(Class<?> clazz){

        ArrayList<String> annotations = new ArrayList<String>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            annotations.add("'" + ObjectInput.getPrompt(field) + "'");
        }
        return annotations;
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return values;
    }
}
