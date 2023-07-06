package com.social.ttinviter.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class StockPrintService {
	
	private final ObjectMapper objMapper;
	
	@Autowired
	public StockPrintService(ObjectMapper objMapper) {
		this.objMapper = objMapper;
	}
	
	public Map<String,Object> sendGetRequest(String url) throws Exception {
		String result = "";
		Map<String, Object> resultMap = new HashMap<>();
		RestTemplate restTemplate = new RestTemplate();
		result = restTemplate.getForObject(url, String.class);
		resultMap = objMapper.readValue(result, HashMap.class);
		
		return resultMap;
	}
	
	
	
}
