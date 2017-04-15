package Entities;

import Annotations.Label;

public class PersonEntity {
	@Label("身份证号")
	private int idNo;

	@Label("姓名")
	private String name;

	@Label("性别")
	private String sex;

	@Label("年龄")
	private Integer age;

	@Label("是否已婚")
	private Boolean isMerried;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public int getIdNo() {
		return idNo;
	}

	public void setIdNo(int idNo) {
		this.idNo = idNo;
	}

	public Boolean getIsMerried() {
		return isMerried;
	}

	public void setIsMerried(Boolean isMerried) {
		this.isMerried = isMerried;
	}

	public String toString() {

		return name + " " + sex + " " + age + " " + idNo + " " + isMerried;
	}
}
