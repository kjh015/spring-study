package com.kjh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 모든 요청에 대해서 Spring Security가 받는다고 설정
@EnableMethodSecurity(prePostEnabled=true) //@PreAuthorize("isAuthenticated()") 가 가능하도록 설정
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorizeHttpRequest) -> 
		authorizeHttpRequest.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()) // root이하("/**") 모든것에 대해 접근허가(permitAll())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))	// 세션 항상 유지
		.csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/**")))	// csrf 허용
		.formLogin(formLogin -> formLogin.loginPage("/user/login").defaultSuccessUrl("/board/list")) // 로그인 페이지 설정, 로그인 성공 시 이동 페이지 설정
		//getMapping으로 로그인 주소가 있어야하고, input form username과 password를 post 방식으로 보내야함.
		//PostMapping이 없어도 UserSecurityService.loadUserByUsername()로 보내짐
		.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/board/list").invalidateHttpSession(true));	//로그아웃 성공시 해당 유저 세션 삭제
		
		return http.build();
	}
	
	// 패스워드를 암호화 인코딩 할 수 있는 객체를 스프링에 전달
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
