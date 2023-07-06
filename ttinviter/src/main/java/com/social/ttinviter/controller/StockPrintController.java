package com.social.ttinviter.controller;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.ttinviter.service.StockPrintService;
import com.social.ttinviter.util.TtinviterException;


@RestController
public class StockPrintController {
	
	@Autowired
	private StockPrintService stockPrintService;
	
	@PostMapping("/stock-print")
	public Map<String,Object> stockPrint(@RequestBody Map<String, String> parameter) throws Exception {
		String strUrl = "https://mis.twse.com.tw/stock/api/getStockInfo.jsp?ex_ch=tse_2330.tw&json=1&delay=0&_=1635167108897";
		Map<String,Object> result = new HashMap<>();
		try {
			result = stockPrintService.sendGetRequest(strUrl);
		} catch (Exception e) {
			throw new TtinviterException("無法獲取twse資料!!");
		}
		
		return result;
	}
	
}
