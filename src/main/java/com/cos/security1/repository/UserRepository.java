package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;
//데이터는 User, pk는 id(정수
//CRUD자체를 지원
//@Repository 생략해도 가지고 있음. 자동 등록됨

public interface UserRepository extends JpaRepository<User, Integer>{

}
