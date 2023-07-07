package com.social.ttinviter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {
	
    @GetMapping(value = {
            "/ttinviter",
            "/ttinviter/welcome/",
            "/ttinviter/sign-in",
            "/ttinviter/sign-up",
            "/ttinviter/*",
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }
    
}
