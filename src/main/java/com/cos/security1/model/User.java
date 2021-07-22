package com.cos.security1.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class User {
	

	@Id // primarykey 등록
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 연결된 DB의 너버링 전략을 따라간다는 설정
	private int id;// auto_increment
	private String username;// 아이디
	private String password;
	private String email;
	private String role;
	@CreationTimestamp
	private Timestamp createDate;

}
