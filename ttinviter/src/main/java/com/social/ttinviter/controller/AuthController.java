package com.social.ttinviter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.social.ttinviter.model.User;
import com.social.ttinviter.service.UserService;
import com.social.ttinviter.util.JwtUtil;
import com.social.ttinviter.util.TtinviterException;

import jakarta.servlet.http.Cookie;


@RestController
public class AuthController {
	
	private static final Logger logger = LogManager.getLogger(AuthController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private User user;
	
	DecodedJWT decoded = null;
	
	@PostMapping("/login")
	public List<Map<String, Object>> login(@RequestBody Map<String, String> parameter) {
		logger.info("=============login controller start=============");
		List<Map<String, Object>> result = new ArrayList<>();
		final Cookie[] cookies = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getCookies();
		DecodedJWT decoded = null;
		boolean loginFlag = false;
		
		try {
			result = userService.login(parameter);
		} catch (Exception e) {
			logger.error(e);
			throw new TtinviterException("登入失敗，請重新登入!!");
		}
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				if ("v1".equals(cookie.getName())) {
					try {
						decoded = JwtUtil.verify(cookie.getValue());
					} catch (Exception e) {
						if (e instanceof TokenExpiredException) {
							throw new TtinviterException("請重新登錄！");
						} else {
							throw e;
						}
					}
					break;
				}
			}
		}
		
		return result;
	}
	
	@PostMapping("/register")
	public Map<String, Object> register(@RequestBody Map<String, String> parameter) {
		boolean regiBool = false;
		Map<String, Object> resultMap = new HashMap<>();
		try {
			regiBool = userService.register(parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resultMap.put("regiflag", regiBool);
		
		return resultMap;
	}
	
}
