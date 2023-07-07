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

import com.social.ttinviter.service.StockPrintService;
import com.social.ttinviter.util.TtinviterException;


@RestController
public class StockPrintController {
	
	private static final Logger logger = LogManager.getLogger(StockPrintController.class);
	
	@Autowired
	private StockPrintService stockPrintService;
	
	@PostMapping("/get-stock")
	public Map<String, Object> stockPrint(@RequestBody Map<String, String> parameter) throws Exception {
		Map<String,Object> dataMap = new HashMap<>();
		Map<String,Object> result = new HashMap<>();
		List<Map<String, Object>> returnResult = new ArrayList<>();
		String code = parameter.get("code");
		String[] inputArray = code.split(",");
		//輸入筆數
		int inputLength = inputArray.length;
		StringBuilder sb = new StringBuilder();
		sb.append("https://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=");
		for (int i=0; i < inputArray.length; i++) {
			sb.append("tse_" + inputArray[i] + ".tw");
			if (inputArray.length != 1 || i != inputArray.length -1) {
				sb.append("|");
			}
		}

		try {
			logger.info("twse_URL Request: " + sb.toString());
			result = stockPrintService.sendGetRequest(sb.toString());
			returnResult = (List<Map<String, Object>>) result.get("msgArray");
		} catch (Exception e) {
			throw new TtinviterException("獲取twse資料有誤!!");
		}
		
		dataMap.put("dataList", returnResult);
		dataMap.put("lostNum", inputLength - returnResult.size());
		
		return dataMap;
	}
	
}
