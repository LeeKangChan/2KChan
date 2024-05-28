package com.chan.demo.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Member {

	@Id
	private String id;
	
	@Column
	private String pwd;
	
	@Column
	private String name;
	
	@Column
	private Integer age;
	
	@Column
	private String post;
	
	@Column
	private String tel;
	
	@Column
	private String sex;
	
	@Column
	private String birth;
	
	@Column
	private String email;
	
	@Column
	private String level;
	
	@Column(name = "reg_date")
	private String regDate;
	
	@Column(name = "mod_id")
	private String modId;
	
	@Column(name = "mod_date")
	private String modDate;
	
	@Column(name = "fail_cnt")
	private Integer failCnt;
	
	@Column(name = "fail_date")
	private String failDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public Integer getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(Integer failCnt) {
		this.failCnt = failCnt;
	}

	public String getFailDate() {
		return failDate;
	}

	public void setFailDate(String failDate) {
		this.failDate = failDate;
	}


	
	
	
}
