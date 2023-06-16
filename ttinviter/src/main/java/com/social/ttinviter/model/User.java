package com.social.ttinviter.model;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class User {
    private Long id;
    
    private String name;
    
    private Integer age;
    
    private String account;
    
    private String password;

    private String email;
    
    private String create_By;
    
    private Date create_Dt;
    
    private String modify_By;
    
    private Date modify_Dt;
    
    public User(){}
    
    public User(String name, Integer age, String createBy){
        this.name = name;
        this.age = age;
        this.create_By = createBy;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCreate_By() {
		return create_By;
	}

	public void setCreate_By(String create_By) {
		this.create_By = create_By;
	}

	public Date getCreate_Dt() {
		return create_Dt;
	}

	public void setCreate_Dt(Date create_Dt) {
		this.create_Dt = create_Dt;
	}

	public String getModify_By() {
		return modify_By;
	}

	public void setModify_By(String modify_By) {
		this.modify_By = modify_By;
	}

	public Date getModify_Dt() {
		return modify_Dt;
	}

	public void setModify_Dt(Date modify_Dt) {
		this.modify_Dt = modify_Dt;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
    
}
