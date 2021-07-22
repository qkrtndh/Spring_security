package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//view를 리턴
public class IndexController {
	@GetMapping({"","/"})
	public String index() {
		//머스테치 사용할 것
		//머스테치 기본폴더 src/main/resources/
		//뷰 리졸버 설정 : templates(prefix)  .mustache(suffix)로 설정하면 끘 근데 생략 가능
		return "index";
	}
}
