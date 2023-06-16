package com.social.ttinviter.controller;

import java.util.HashMap;
import java.util.Map;

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

import jakarta.servlet.http.Cookie;


@RestController
public class AuthController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private User user;
	
	DecodedJWT decoded = null;
	
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Map<String, String> parameter) {
		Map<String, Object> resultMap = new HashMap<>();
		final Cookie[] cookies = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getCookies();
		DecodedJWT decoded = null;
		
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				if ("v1".equals(cookie.getName())) {
					try {
						decoded = JwtUtil.verify(cookie.getValue());
					} catch (Exception e) {
						if (e instanceof TokenExpiredException) {
							System.out.println("請重新登錄！");
						} else {
							throw e;
						}
					}
					break;
				}
			}
		}
		
		try {
			userService.login(parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@PostMapping("/register")
	public void register(@RequestBody Map<String, String> parameter) {
		try {
			userService.register(parameter);
			System.out.println("register Finish!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
