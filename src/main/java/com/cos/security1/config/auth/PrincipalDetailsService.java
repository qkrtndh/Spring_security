package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;


//시큐리티 설정에서 loginprocurl 요청이 오면 자동으로 userdetailsService 타입으로 ioc 되어있는  loadUserbyusername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	//로그인 창의 name=username이 그대로 넘어오므로 이름 설성 주의
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUsername(username);
		if (userEntity!=null) {
			//리턴된 값이 Authentication내부로 들어감.
			//Authentication은 session내부로 들어감.
			return new PrincipalDetails(userEntity);
		}
		return null;
	}

}
