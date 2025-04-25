package com.kjh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller		//boot는 자동 ComponentScan. @Configure파일 필요없음.
public class DemoController {
	@ResponseBody //리턴하는 것 자체를 주겠다는 의미.
	@GetMapping("/")
	public String home() {
		return "Hello!!";
	}
}
