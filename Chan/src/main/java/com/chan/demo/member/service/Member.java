package com.chan.demo.member.service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
	private int age;
	
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
	
	@Column
	private String reg_date;
	
	@Column
	private String mod_id;
	
	@Column
	private String mod_date;
	
	@Column
	private int fail_cnt;
	
	@Column
	private String fail_date;

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
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

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getMod_id() {
		return mod_id;
	}

	public void setMod_id(String mod_id) {
		this.mod_id = mod_id;
	}

	public String getMod_date() {
		return mod_date;
	}

	public void setMod_date(String mod_date) {
		this.mod_date = mod_date;
	}

	public int getFail_cnt() {
		return fail_cnt;
	}

	public void setFail_cnt(int fail_cnt) {
		this.fail_cnt = fail_cnt;
	}

	public String getFail_date() {
		return fail_date;
	}

	public void setFail_date(String fail_date) {
		this.fail_date = fail_date;
	}

	
	
	
}
