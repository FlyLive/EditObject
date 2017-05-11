package Back.Service;

import Back.DataBase.DataBaseDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Created by 15852 on 2017/4/27.
 */
public class ObjectService {
    private DataBaseDAO _db = new DataBaseDAO();
    private static final String filePath = "D:\\备份文件\\我的电脑重要文件\\Java\\EditObject\\src\\Back\\Service\\Objects.xml";
    private static final ObjectDisplay objectDisplay = new ObjectDisplay();

    //获取所有对象的名称
    public ArrayList<String> getAllObjectName() {
        ArrayList<String> objectNames = new ArrayList<String>();

        try {
            SAXReader sr = new SAXReader();
            File file = new File(filePath);
            InputStream input = new FileInputStream(file);
            Document doc = sr.read(input);

            Element root = doc.getRootElement();
            List<Element> elements = (List<Element>) root.elements("object");

            for (int i = 0; i < elements.size(); i++) {
                String name = elements.get(i).attribute("name").getValue();
                objectNames.add("'" + name + "'");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return objectNames;
    }

    //获取类的所有字段
    public static ArrayList<String> getTips(String objectName) {
        ArrayList<String> tips = new ArrayList<String>();
        try {
            Class<?> clazz = Class.forName("ViewModels." + objectName + "Entity");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                tips.add(field.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tips;
    }

    //获取所有字段注解
    public ArrayList<String> getTitle(String objectName) {
        ArrayList<String> titles = new ArrayList<String>();
        try {
            Class<?> clazz = Class.forName("ViewModels." + objectName + "Entity");
            titles.addAll(objectDisplay.getAnnotations(clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titles;
    }

    //获取所有字段注解
    public static ArrayList<String> getAnnotations(String objectName) {
        ArrayList<String> annotations = new ArrayList<String>();
        try {
            Class<?> clazz = Class.forName("ViewModels." + objectName + "Entity");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                annotations.add(ObjectInput.getPrompt(field));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return annotations;
    }

    // 显示表所有信息
    public List<Object> showAll(String objectName) {
        ArrayList<Object> result = new ArrayList<Object>();
        try {
            String sql = "select * from " + objectName;
            ResultSet rs = null;
            rs = _db.executeSelectSQL(sql);
            if (rs != null) {
                result.addAll(objectDisplay.translateInfo(rs, Class.forName("ViewModels." + objectName + "Entity")));
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 增加
    public boolean create(String objectName, Object[] inputs, Object[] options) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("ViewModels." + objectName + "Entity");
            // 拼接创建Object的SQL语言
            String sql = CreateObjectSQLString(objectName, options);

            // 写入数据库
            int count = _db.executeUpdateSQL(sql, inputs);

            return count > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 拼接添加对象SQL语句
    public String CreateObjectSQLString(String objName, Object[] options) {
        String sql = "insert into " + objName + "(";

        for (int i = 0; i < options.length; i++) {
            sql += options[i].toString() + ",";
        }

        sql = sql.substring(0, sql.length() - 1);
        sql += ") values(";

        for (int i = 0; i < options.length; i++) {
            sql += "?,";
        }

        sql = sql.substring(0, sql.length() - 1);
        sql = sql + ");";

        return sql;
    }

    // 删除
    public boolean deleteObject(String objectName, int id) {
        try {
            String sql = "delete from " + objectName + " where idNo=?";
            int count = _db.executeUpdateSQL(sql, id);

            return count > 0 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    // 修改
    public boolean update(String objectName, Object[] inputs, Object[] options) {
        int id = 0;
        Class<?> clazz = null;
        try {
            clazz = Class.forName("ViewModels." + objectName + "Entity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        // 修改值列表

        String sqlCenter = "";

        // 拼接更新部分SQL语句
        for (int i = 0; i < options.length; i++) {
            sqlCenter += options[i].toString() + "=?,";
            if (options[i].toString().equals("idNo")) {
                id = Integer.parseInt(inputs[i].toString());
            }
        }

        sqlCenter = sqlCenter.substring(0, sqlCenter.length() - 1);

        String sql = "update " + objectName + " set " + sqlCenter
                + " where idNo=" + id;

        // 对数据库进行操作
        int count = _db.executeUpdateSQL(sql, inputs);

        return count > 0 ? true : false;
    }

    // 根据ID查找
    public Object searchById(String objectName, int id) {
        Object result = new Object();
        ResultSet rs = null;
        try {
            Class<?> clazz = Class.forName("ViewModels." + objectName + "Entity");
            String sql = "select * from " + objectName + " where idNo=?";

            rs = _db.executeSelectSQL(sql, id);

            if (rs != null) {
                result = objectDisplay.translateInfo(rs, clazz).get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 查找
    public ArrayList<Object> search(String objectName, String search) {
        ArrayList<Object> result = new ArrayList<Object>();
        Class<?> clazz = null;
        ResultSet rs = null;
        try {
            clazz = Class.forName("ViewModels." + objectName + "Entity");
            String sql = "select * from " + objectName;

            rs = _db.executeSelectSQL(sql);

            result.addAll(objectDisplay.searchAll(rs, clazz, search));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
