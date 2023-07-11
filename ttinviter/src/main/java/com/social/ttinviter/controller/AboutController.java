package com.social.ttinviter.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutController {
	
	private static final Logger logger = LogManager.getLogger(AboutController.class);
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@PostMapping("/get-resume")
	public ResponseEntity<byte[]> getResume() throws IOException {
			String filePath = "classpath:resume.pdf";
			Resource resource = resourceLoader.getResource(filePath);
			logger.debug("=====================我在這===============================");
		    InputStream inputStream = resource.getInputStream();
		    byte[] fileContent = IOUtils.toByteArray(inputStream);
			logger.debug("=====================我過了===============================");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", "resume.pdf");
			
	        return ResponseEntity.ok()
	                .headers(headers)
	                .body(fileContent);
        
	}
	
}
