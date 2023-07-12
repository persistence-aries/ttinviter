package com.social.ttinviter.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.social.ttinviter.service.StockPrintService;
import com.social.ttinviter.util.DateUtil;
import com.social.ttinviter.util.TtinviterException;


@RestController
public class StockPrintController {
	
	private static final Logger logger = LogManager.getLogger(StockPrintController.class);
	
	@Autowired
	private StockPrintService stockPrintService;
	
	@PostMapping("/get-stock")
	public Map<String, Object> stockPrint(@RequestBody Map<String, String> parameter) throws Exception {
		Map<String,Object> dataMap = new HashMap<>();
		List<Map<String, Object>> returnResult = new ArrayList<>();
		String code = parameter.get("code");
		String[] inputArray = code.split(",");
		//輸入筆數
		int inputLength = inputArray.length;
		try {
			String url = stockPrintService.combinRequestUrl(inputArray);
			Map<String,Object> result = stockPrintService.sendGetRequest(url);
			returnResult = (List<Map<String, Object>>) result.get("msgArray");
		} catch (Exception e) {
			throw new TtinviterException("獲取twse資料有誤!!");
		}
		dataMap.put("dataList", returnResult);
		dataMap.put("lostNum", inputLength - returnResult.size());
		
		return dataMap;
	}
	
	
	@PostMapping("/stock-print")
	public ResponseEntity<InputStreamResource> print(@RequestBody Map<String, String> parameter) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String reqDataList = parameter.get("dataList");
		String reqDataListToStr = reqDataList.replace("&quot;", "\"");
		
		Gson gson = new Gson();
		Gson g = new GsonBuilder().create();
		Type mapType = new TypeToken<List<Map<String, Object>>>() { 
		}.getType();
		
		List<Map<String, Object>> dataList = g.fromJson(reqDataListToStr, mapType);
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		int rowIndex = 0;
		
		XSSFRow row = sheet.createRow(rowIndex);
		XSSFCell titleCell = row.createCell(0);
		titleCell.setCellValue("即時股票查詢");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		XSSFRow headRow = sheet.createRow(++rowIndex);
		String [] excelHeader = 
			{
				"股票代號	",
				"公司簡稱",
				"成交價",
				"成交量",
				"累積成交量",
				"開盤價",
				"最高價",
				"最低價",
				"昨收價",
				"資料更新時間",
			};
		/*塞入標頭*/
		for (int i = 0; i < excelHeader.length; i++) {
		   XSSFCell hc = headRow.createCell(i);
		   hc.setCellValue(excelHeader[i]);
		}
		
		/**********************************塞資料*******************************************/
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> vo = dataList.get(i);
			row = sheet.createRow(i + 2);
			int index = 0;
			row.createCell(index++).setCellValue(vo.get("c") == null ? "" : vo.get("c").toString());
			row.createCell(index++).setCellValue(vo.get("n") == null ? "" : vo.get("n").toString());
			row.createCell(index++).setCellValue(vo.get("z") == null ? "" : vo.get("z").toString());
			row.createCell(index++).setCellValue(vo.get("tv") == null ? "" : vo.get("tv").toString());
			row.createCell(index++).setCellValue(vo.get("v") == null ? "" : vo.get("v").toString());
			row.createCell(index++).setCellValue(vo.get("o") == null ? "" : vo.get("o").toString());
			row.createCell(index++).setCellValue(vo.get("h") == null ? "" : vo.get("h").toString());
			row.createCell(index++).setCellValue(vo.get("l") == null ? "" : vo.get("l").toString());
			row.createCell(index++).setCellValue(vo.get("y") == null ? "" : vo.get("y").toString());
			row.createCell(index++).setCellValue(vo.get("tlong") == null ? "" : DateUtil.msDateFormat(Long.parseLong(vo.get("tlong").toString())));
			//自動設定欄寬
			sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 17 / 10);
		}
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stock_print_"+ sdf.format(new Date()) +".xlsx");
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(inputStream));

	}
	
}
