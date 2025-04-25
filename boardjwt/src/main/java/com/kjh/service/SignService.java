package com.kjh.service;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kjh.common.CommonResponse;
import com.kjh.dto.SignInResultDto;
import com.kjh.dto.SignUpResultDto;
import com.kjh.entity.BoardUser;
import com.kjh.repository.BoardUserRepository;
import com.kjh.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignService {
	private final BoardUserRepository boardUserRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	
	public SignUpResultDto signUp(String id, String password, String name, String role) {
		BoardUser boardUser;
		
		if (role.equalsIgnoreCase("admin")) {
			boardUser = BoardUser.builder().uid(id).name(name).password(passwordEncoder.encode(password))
					.roles(Collections.singletonList("ROLE_ADMIN")).build();
		} else {
			boardUser = BoardUser.builder().uid(id).name(name).password(passwordEncoder.encode(password))
					.roles(Collections.singletonList("ROLE_USER")).build();
		}

		BoardUser savedUser = boardUserRepository.save(boardUser);
		SignUpResultDto signUpResultDto = new SignUpResultDto();

		if (!savedUser.getName().isEmpty()) {
			setSuccessResult(signUpResultDto);
		} else {
			setFailResult(signUpResultDto);
		}
		return signUpResultDto;
	}
	
	public SignInResultDto signIn(String id, String password) throws RuntimeException {
		BoardUser boardUser = boardUserRepository.getByUid(id);
		
		if(!passwordEncoder.matches(password, boardUser.getPassword())) {
			throw new RuntimeException();
		}
		SignInResultDto signInResultDto = SignInResultDto.builder()
				.token(jwtTokenProvider.createToken(String.valueOf(boardUser.getUid()), boardUser.getRoles())).build(); // null일 경우 대비하여 String.valueOf()로 감쌈
		setSuccessResult(signInResultDto);
		
		return signInResultDto;
	}
	
	private void setSuccessResult(SignUpResultDto result) {
		result.setSuccess(true);
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setMsg(CommonResponse.SUCCESS.getMsg());
		
	}
	
	private void setFailResult(SignUpResultDto result) {
		result.setSuccess(false);
		result.setCode(CommonResponse.FAIL.getCode());
		result.setMsg(CommonResponse.FAIL.getMsg());
	}
}
