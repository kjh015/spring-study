package com.kjh.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@Entity
public class BoardUser {
	@Id //PK로 지정
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 자동증가
	private Long id;
	
	@Column(unique=true)
	private String username;
	
	private String passward;
	
	@Column(unique=true)
	private String email;
}
// html input태그의 username이 userCreateForm.username에 들어감