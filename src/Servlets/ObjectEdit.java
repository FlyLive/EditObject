package Servlets;

import  DataBase.DataBaseDAO;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Scanner;

public class ObjectEdit {
	private static final DataBaseDAO db = new DataBaseDAO();
	private static final ObjectInput objectInput = new ObjectInput();
	private static final ObjectDisplay objectDisplay = new ObjectDisplay();

	// 显示表所有信息
	public void showAll(Class<?> clazz) {
		String className = clazz.getSimpleName();
		String sql = "select * from " + className;
		ResultSet rs = null;
		rs = db.executeSelectSQL(sql);
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
		int count = db.executeUpdateSQL(sql, fieldValues);
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
		int count = db.executeUpdateSQL(sql, no);

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
		int count = db.executeUpdateSQL(sql, fields);
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

		rs = db.executeSelectSQL(sql, idNo);

		if (rs != null) {
			objectDisplay.displayInfo(rs, clazz);
		}
	}

}
