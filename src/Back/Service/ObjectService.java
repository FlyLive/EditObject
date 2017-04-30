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
import java.util.Scanner;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * Created by 15852 on 2017/4/27.
 */
public class ObjectService {
    private DataBaseDAO _db = new DataBaseDAO();
    private static final String filePath = "";
    private static final ObjectInput objectInput = new ObjectInput();
    private static final ObjectDisplay objectDisplay = new ObjectDisplay();

    public ArrayList<String> getAllObjectName() {
        ArrayList<String> objectNames = new ArrayList<String>();

        try {
            SAXReader sr = new SAXReader();
            File file = new File(filePath);
            InputStream input = new FileInputStream(file);
            Document doc = sr.read(input);

            Element root = doc.getRootElement();
            List<Element> elements = (List<Element>)root.elements("object");

            for(int i = 0;i < elements.size();i++){
                String name = elements.get(i).attribute("name").getValue();
                objectNames.add(name);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return objectNames;
    }

    // 显示表所有信息
    public void showAll(String className) {
        String sql = "select * from " + className;
        ResultSet rs = null;
        rs = _db.executeSelectSQL(sql);
        if (rs != null) {


        } else {
            System.out.println("暂无信息，请添加!");
        }
    }

    // 增加
    public void create(Class<?> clazz) {
        // 输入初始化
        Object obj = objectInput.createObject(clazz);

        Object[] fieldValues = objectDisplay.getFields(obj, clazz);

        // 拼接为SQL语言
        String sql = CreateObjectSQLString(clazz, fieldValues);

        // 写入数据库
        int count = _db.executeUpdateSQL(sql, fieldValues);
        if (count > 0) {
            System.out.println("添加成功！");
        } else {
            System.out.println("添加失败！");
        }
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
    public void delete(Class<?> clazz) {
        String className = clazz.getSimpleName();
        Scanner input = new Scanner(System.in);

        System.out.println("请输入查找对象的号码：");
        int no = (int) objectInput.getCheckedInput("Integer");

        String sql = "delete from " + className + " where idNo=?";
        int count = _db.executeUpdateSQL(sql, no);

        if (count > 0) {
            System.out.println("成功删除号码为 " + no + " 的对象");
        } else {
            System.out.println("未找到Id为: " + no + " 的对象");
        }
    }

    // 修改
    public void update(Class<?> clazz) {
        String className = clazz.getSimpleName();
        // 修改值列表

        System.out.println("请输入修改对象的ID:");
        int idNo = (int) objectInput.getCheckedInput("Integer");

        Object obj = objectInput.modifyObject(clazz);

        String sqlCenter = "";
        Object[] fields = objectDisplay.getFields(obj, clazz);

        // 拼接更新部分SQL语句
        int index = 0;
        for (Field field : clazz.getDeclaredFields()) {
            sqlCenter += field.getName() + "=?,";
            if (field.getName().equals("idNo")) {
                fields[index] = idNo;
            }
            index++;
        }

        sqlCenter = sqlCenter.substring(0, sqlCenter.length() - 1);

        String sql = "update " + className + " set " + sqlCenter
                + " where idNo=" + idNo;

        // 对数据库进行操作
        int count = _db.executeUpdateSQL(sql, fields);
        if (count > 0) {
            System.out.println("成功修改ID为: " + idNo + " 的对象");
        } else {
            System.out.println("修改失败!");
        }
    }

    // 查找
    public void select(Class<?> clazz) {
        String className = clazz.getSimpleName();
        Scanner input = new Scanner(System.in);
        ResultSet rs = null;
        String sql = "select * from " + className + " where idNo=?";

        System.out.println("请输入查找对象的号码：");
        int idNo = (int) objectInput.getCheckedInput("Integer");

        rs = _db.executeSelectSQL(sql, idNo);

        if (rs != null) {
            //objectDisplay.displayInfo(rs, clazz);
        }
    }
    //删除对象
    public boolean deletObject(String objectName,int id){
        try{

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
