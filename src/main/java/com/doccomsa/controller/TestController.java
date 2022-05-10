package com.doccomsa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test/*")
@Controller
public class TestController {

	@GetMapping("/doA")
	public void doA() {
		
	}
	
	@GetMapping("/doB")
	public void doB() {
		
	}
	
	@GetMapping("/doC")
	public void doC() {
		
	}
}
