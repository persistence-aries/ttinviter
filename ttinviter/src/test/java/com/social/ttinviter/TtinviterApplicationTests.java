package com.social.ttinviter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.social.ttinviter.service.UserService;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TtinviterApplicationTests {

	   @Autowired
	    private MockMvc mockMvc;
		
		@Autowired
		private UserService userService;
		
		
		@Test
		public void testLogin() throws Exception {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			
			JSONObject request = new JSONObject()
									.put("account", "wwwact")
									.put("password", "zxc123");
			
			RequestBuilder requestBuilder = 
						MockMvcRequestBuilders
							.post("/login")
							.headers(httpHeaders)
							.content(request.toString());
			
			/*
			 * 透過 MockMvc 元件來發出模擬請求
			 * 透過 perform 方法，傳入上一節建立的請求資料
			 * andExpect 方法進行回應資料的驗證
			 */
			mockMvc.perform(requestBuilder)
			.andDo(print())
			.andExpect(status().isCreated())
	        .andExpect(jsonPath("$.id").hasJsonPath())
	        .andExpect(jsonPath("$.account").value(request.getString("account")))
	        .andExpect(jsonPath("$.password").value(request.getInt("password")))
	        .andExpect(header().exists(HttpHeaders.LOCATION))
	        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
									
		} 

}
