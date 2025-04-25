package com.kjh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResultDto {
	//DTO Data Transfer Object => Java Bean
	private boolean success;	// sign up 성공/실패 여부
	private int code;
	private String msg;
}
