package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

//시큐리티가 /login을 낚아채서 로그인을 진행
//로그인이 완료되면 시큐리티 session을 만들어준다.(Security ContextHolder)
//세션에 들어갈 수 있는 오브젝트가 정해져있다 Authentication 타입 객체
//Authentication 타입 객체 안에 User정보가 있어야 한다.
//User 오브젝트 타입은 UserDetails 타입이어야 한다.
//Security Sesstion -> Authentication -> UserDetails(PrincipalDetails)

public class PrincipalDetails implements UserDetails {

	// 유저정보는 유저 객체가 가지고 있다.
	private User user;// 콤포지션

	// 호출시 반드시 유저정보를 담을 수 있도록 제한
	public PrincipalDetails(User user) {
		this.user = user;
	}

	// 해당 유저의 권한을 리턴하는 함수
	// 현재 권한은 ROLE 이고 string 이므로 반환이 불가
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
