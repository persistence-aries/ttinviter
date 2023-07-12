package com.social.ttinviter.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.ttinviter.controller.StockPrintController;


@Service
public class StockPrintService {
	
	private static final Logger logger = LogManager.getLogger(StockPrintService.class);
	
	@Autowired
	private final ObjectMapper objMapper;
	
	public StockPrintService(ObjectMapper objMapper) {
		this.objMapper = new ObjectMapper();
	}
	
	public Map<String,Object> sendGetRequest(String url) throws Exception {
		String result = "";
		Map<String, Object> resultMap = new HashMap<>();
		RestTemplate restTemplate = new RestTemplate();
		result = restTemplate.getForObject(url, String.class);
		resultMap = objMapper.readValue(result, HashMap.class);
		
		return resultMap;
	}
	
	public String combinRequestUrl(String[] inputArray) {
		StringBuilder sb = new StringBuilder();
		sb.append("https://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=");
		for (int i=0; i < inputArray.length; i++) {
			sb.append("tse_" + inputArray[i].trim() + ".tw");
			if (inputArray.length != 1 || i != inputArray.length -1) {
				sb.append("|");
			}
		}
		logger.debug("twse_URL Request: " + sb.toString());
		return sb.toString();
	}
	
	
	
}
