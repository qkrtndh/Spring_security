package com.cos.security1.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	// 구글로부터 받은 userRequest 데이터에대한 후처리되는 함수
	//함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequeset: "+userRequest.getClientRegistration());//registrationId로 어떤 OAuth로 로그인 했는지 확인 가능
		System.out.println("getAccessToken: "+userRequest.getAccessToken().getTokenValue());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		//구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code리턴(OAuth-Client 라이브러리) -> 액세스토큰 요청
		//userRequest 정보 -> LoadUser함수 사용 ->회원 프로필 받음 
		System.out.println("getAttributes: "+oauth2User.getAttributes());
		
		String provider = userRequest.getClientRegistration().getClientId();//google
		String providerId = oauth2User.getAttribute("sub");
		String username = provider+"_"+providerId;
		String passwrod = bCryptPasswordEncoder.encode("getinthere");
		String email = oauth2User.getAttribute("email");
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		if(userEntity == null)
		{
			System.out.println("최초 가입");
			userEntity = User.builder()
					.username(username)
					.password(passwrod)
					.email(email)
					.role(role)
					.provider(providerId)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity);
		}else {
			System.out.println("기회원 자동 가입 되어있음");
		}
		
		return new PrincipalDetails(userEntity,oauth2User.getAttributes());
	}
}
