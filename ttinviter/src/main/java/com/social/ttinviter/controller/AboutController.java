package com.social.ttinviter.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.ttinviter.service.AboutService;

@RestController
public class AboutController {
	
	private static final Logger logger = LogManager.getLogger(AboutController.class);
	
	@Autowired
	private AboutService aboutService;
	
	@PostMapping("/get-resume")
	public ResponseEntity<byte[]> getResume() throws IOException {
			String filePath = "classpath:resume.pdf";
		    byte[] fileContent = aboutService.getFileContent(filePath);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", "resume.pdf");
			
	        return ResponseEntity.ok()
	                .headers(headers)
	                .body(fileContent);
	}
	
}
