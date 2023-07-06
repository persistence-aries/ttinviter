package com.social.ttinviter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.social.ttinviter.controller.AuthController;
import com.social.ttinviter.mapper.UsersMapper;
import com.social.ttinviter.repository.UserRepository;
import com.social.ttinviter.util.CookieUtil;
import com.social.ttinviter.util.JwtUtil;
import com.social.ttinviter.util.TtinviterException;


@Service
public class UserService {
	
	private static final Logger logger = LogManager.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepository;
	
	public boolean register(Map<String, String> parameter) {
		boolean regiBool = false;
		String name = parameter.get("name").toString();
		String age = parameter.get("age").toString();
		String email = parameter.get("email").toString();
		String account = parameter.get("account").toString();
		String password = parameter.get("password").toString();
		
		try {
			userRepository.addUser(name, Integer.parseInt(age), account, password, email, "SYSTEM");
			regiBool = true;
			logger.debug("註冊成功!!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new TtinviterException("註冊失敗!!");
		}
		
		return  regiBool;
	}
	
	public List<Map<String, Object>> login(Map<String, String> parameter) {
		List<Map<String, Object>> result = new ArrayList<>();
		boolean loginSuccess = false;
		String account = parameter.get("account").toString();
		String password = parameter.get("password").toString();
		try {
			result = userRepository.selectUser(account, password);
			if (result.size() > 0) {
				logger.debug("登入成功!!");
				loginSuccess = true;
				CookieUtil.setLoginDataToCookie(account);
			} else {
				throw new TtinviterException("輸入帳密有誤，請重新輸入!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TtinviterException("登入失敗!!");
		}
		
		
		return result;
	} 
	
}
