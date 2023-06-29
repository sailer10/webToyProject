package com.mysite.PP;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class HelloContorller {
	@GetMapping("/hello")
	@ResponseBody
	public String Hello() {
		// GetMapping, ResponseBody 테스트용...
		return "Hello World!";
	}
}
