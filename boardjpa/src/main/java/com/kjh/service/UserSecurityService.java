package com.kjh.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kjh.entity.BoardUser;
import com.kjh.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService{
	private final UserRepository userRepository;
	
	public UserSecurityService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<BoardUser> _boardUser = userRepository.findByUsername(username);
		if(_boardUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		BoardUser boardUser = _boardUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if("admin".equals(username)) { // 관리자
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getRole()));
		}
		else { // 일반 유저
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getRole()));
		}
		
		return new User(boardUser.getUsername(), boardUser.getPassward(), authorities); //UserName과 Passward가 일치하는지 스프링이 알아서 해줌
	}
	
	
}
//need
//@Valid
//authorities
