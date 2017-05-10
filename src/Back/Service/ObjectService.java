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
    private static final ObjectInput objectInput = new ObjectInput();
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

    //获取该对象的信息提示
    public ArrayList<String> getAnnotations(String objectName){
        ArrayList<String> tips = new ArrayList<String>();
        try {
            tips.addAll(objectDisplay.getAnnotations(Class.forName("ViewModels." + objectName + "Entity")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tips;
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
    public boolean create(Class<?> clazz, String[] inputs) {
        // 输入初始化
        Object obj = objectInput.createObject(clazz, inputs);

        Object[] fieldValues = objectDisplay.getFields(obj, clazz);

        // 拼接为SQL语言
        String sql = CreateObjectSQLString(clazz, fieldValues);

        // 写入数据库
        int count = _db.executeUpdateSQL(sql, fieldValues);

        return count > 0 ? true : false;
    }

    // 拼接添加对象SQL语句
    public String CreateObjectSQLString(Class<?> clazz, Object[] fieldValues) {
        String className = clazz.getSimpleName();
        String sql = "insert into " + className + " values(";

        for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
            sql += "?,";
        }

        sql = sql.substring(0, sql.length() - 1);
        sql = sql + ");";

        return sql;
    }

    // 删除
    public boolean delete(String objectName, int id) {
        try {
            String sql = "delete from " + objectName + " where idNo=?";
            int count = _db.executeUpdateSQL(sql, id);

            return count > 0 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    // 修改
    public boolean update(String objectName, int id, String[] inputs) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("ViewModels." + objectName + "Entity");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        // 修改值列表
        Object obj = objectInput.modifyObject(clazz, id, inputs);

        String sqlCenter = "";
        Object[] fields = objectDisplay.getFields(obj, clazz);

        // 拼接更新部分SQL语句
        int index = 0;
        for (Field field : clazz.getDeclaredFields()) {
            sqlCenter += field.getName() + "=?,";
            if (field.getName().equals("idNo")) {
                fields[index] = id;
            }
            index++;
        }

        sqlCenter = sqlCenter.substring(0, sqlCenter.length() - 1);

        String sql = "update " + objectName + " set " + sqlCenter
                + " where idNo=" + id;

        // 对数据库进行操作
        int count = _db.executeUpdateSQL(sql, fields);

        return count > 0 ? true : false;
    }

    // 查找
    public ArrayList<Object> search(String objectName, String search) {
        ArrayList<Object> result = new ArrayList<Object>();
        ResultSet rs = null;
        try {
            String sql = "select * from " + objectName + " where idNo=?";

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
}
