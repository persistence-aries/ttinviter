package com.social.ttinviter.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class AboutService {
	
	private static final Logger logger = LogManager.getLogger(AboutService.class);
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public byte[] getFileContent(String filePath) throws IOException {
		byte[] fileContent = null;
		Resource resource = resourceLoader.getResource(filePath);
		logger.debug("getFileContent: " + filePath);
		InputStream inputStream = resource.getInputStream();
		fileContent = IOUtils.toByteArray(inputStream);
		
		return fileContent;
	}
	
}
