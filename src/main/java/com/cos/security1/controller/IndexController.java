package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller // view를 리턴
public class IndexController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({ "", "/" })
	public String index() {
		// 머스테치 사용할 것
		// 머스테치 기본폴더 src/main/resources/
		// 뷰 리졸버 설정 : templates(prefix) .mustache(suffix)로 설정하면 끘 근데 생략 가능
		return "index";
	}

	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}

	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}

	@PostMapping("/join")
	public String join(User user) {
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user);// 여기까지가 회원가입. 하지만 비밀번호가 암호회되어있지 않아 시큐리티 로그인은 불가능한상태.
		// encode를 통해 암호화하여 저장하므로 로그인은 가능
		return "redirect:/loginForm";
	}
	
	@Secured("ROLE_ADMIN")//특정메소드에만 권한 제한을 걸어둘 때 사용
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")//함수실행 직전에 제한
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "data";
	}
}
