package com.social.ttinviter.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public final class CookieUtil {
	
	private CookieUtil() { }
	
	public static Cookie getCookie(final String k, final String v) {
		Cookie c = new Cookie(k, v);
		c.setHttpOnly(true);
		c.setSecure(false);
		c.setPath("/ttinviter");
		return c;
	}
	
	public static void setLoginDataToCookie(final String account) {
		if (account != null) {
			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
			if (StringUtils.isBlank(account)) {
				Cookie c = getCookie("t1", "");
				c.setHttpOnly(true);
				c.setMaxAge(0);	// 讓token過期失效
				response.addCookie(c);			
			} else {				
				response.addCookie(getCookie("t1", JwtUtil.sign("uid", account)));
			}
		}
	}
	
}
