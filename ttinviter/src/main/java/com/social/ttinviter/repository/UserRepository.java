package com.social.ttinviter.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.social.ttinviter.mapper.UsersMapper;

@Repository
public class UserRepository {
	@Autowired
	private UsersMapper usersMapper;
	
	public void addUser(String name, Integer age, String account, String password, String email, String create_by) {
		usersMapper.insert(name, age, account, password, email, create_by);
	}
	
	public List<Map<String, Object>> selectUser(String account, String password) {
		List<Map<String, Object>> result = new ArrayList<>();
		result = usersMapper.findByAccount(account, password);
		return result;
	}
}
