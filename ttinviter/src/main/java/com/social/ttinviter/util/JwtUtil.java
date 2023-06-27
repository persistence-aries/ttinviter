package com.social.ttinviter.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtUtil {

	private static final String DEFAULT_ISSUER = "ttinviter";
	
	private static final String HMAC256 = "LQGhw3C+jcu+Ju26UmGGS/Q1BmyVk3xu";
	private static Algorithm algo = Algorithm.HMAC256(HMAC256);
	
	private static final int EXPIRE_HR = 0; 	// timeout 時
	private static final int EXPIRE_MIN = 240; 	// timeout 分
	private static final int EXPIRE_SCD = 0; 	// timeout 秒

	private JwtUtil() { }

	/**
	 * 加密一組資料
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String sign(final String key, final String value) {
		return sign(DEFAULT_ISSUER, Map.of(key, value));
	}
	
	/**
	 * 加密一組資料
	 * 
	 * @param issuer
	 * @param key
	 * @param value
	 * @return
	 */
	public static String sign(final String issuer, final String key, final String value) {
		return sign(StringUtils.defaultIfBlank(issuer, DEFAULT_ISSUER), Map.of(key, value));
	}

	/**
	 * 加密多組資料
	 * 
	 * @param map
	 * @returns
	 */
	public static String sign(final Map<String, String> map) {
		if (map != null) {			
			Builder bd = JWT.create().withIssuer(DEFAULT_ISSUER);
			// 多個claim
			map.keySet().forEach(key -> {
				bd.withClaim(key, map.get(key));				
			});
			return bd.withExpiresAt(DateUtil.addTimeHMS(DateUtil.getNow(), EXPIRE_HR, EXPIRE_MIN, EXPIRE_SCD))
						.sign(algo);
		}
		return null;
	}
	
	/**
	 * 加密多組資料
	 * 
	 * @param issuer
	 * @param map
	 * @returns
	 */
	public static String sign(final String issuer, final Map<String, String> map) {
		if (map != null) {			
			Builder bd = JWT.create().withIssuer(StringUtils.defaultIfBlank(issuer, DEFAULT_ISSUER));
			// 多個claim
			map.keySet().forEach(key -> {
				bd.withClaim(key, map.get(key));				
			});
			return bd.withExpiresAt(DateUtil.addTimeHMS(DateUtil.getNow(), EXPIRE_HR, EXPIRE_MIN, EXPIRE_SCD))
						.sign(algo);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param token
	 * @return
	 */
	public static DecodedJWT verify(final String token) {
		return verify(token, DEFAULT_ISSUER);
	}

	/**
	 * 解密
	 * 
	 * @param token
	 * @param issuer
	 * @param secret
	 * @return
	 */
	public static DecodedJWT verify(final String token, final String issuer) {
		return JWT.require(algo)
					.withIssuer(StringUtils.defaultIfBlank(issuer, DEFAULT_ISSUER))
					.build()
					.verify(token);
	}
    
    
}
