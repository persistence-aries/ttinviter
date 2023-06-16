package com.social.ttinviter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.social.ttinviter.mapper.UsersMapper;
import com.social.ttinviter.repository.UserRepository;
import com.social.ttinviter.util.CookieUtil;
import com.social.ttinviter.util.JwtUtil;

import jakarta.servlet.http.Cookie;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
//	@Autowired
//	private UsersMapper usersMapper;
//	
//	public void addUser(String name, Integer age, String account, String password, String email, String create_by) {
//		usersMapper.insert(name, age, account, password, email, create_by);
//	}
//	
//	public List<Map<String, Object>> selectUser(String account) {
//		List<Map<String, Object>> result = new ArrayList<>();
//		result = usersMapper.findByAccount(account);
//		return result;
//	}
	
	public boolean register(Map<String, String> parameter) {
		boolean regiBool = false;
		String name = parameter.get("name").toString();
		String age = parameter.get("age").toString();
		String email = parameter.get("email").toString();
		String account = parameter.get("account").toString();
		String password = parameter.get("password").toString();
		
		try {
			userRepository.addUser(name, Integer.parseInt(age), account, password, email, "SYSTEM");
			System.out.println("註冊成功!!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("註冊失敗!!");
		}
		
		return  regiBool;
	}
	
	public Object login(Map<String, String> parameter) {
		String account = parameter.get("account").toString();
		String password = parameter.get("password").toString();
		try {
			List<Map<String, Object>> result = userRepository.selectUser(account, password);
			if (result.size() > 0) {
				System.out.println("登入成功!!");
				
				CookieUtil.setLoginDataToCookie(account);
			} else {
				System.out.println("輸入帳密有誤，請重新輸入!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("登入失敗!!");
		}
		
		
		return "";
	} 
	
}
