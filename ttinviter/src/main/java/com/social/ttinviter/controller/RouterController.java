package com.social.ttinviter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {
	
    @GetMapping(value = {
            "/ttinviter",
            "/ttinviter/pages/welcome/",
            "/ttinviter/pages/sign-in",
            "/ttinviter/pages/sign-up",
            "/ttinviter/pages/*",
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }
    
}
