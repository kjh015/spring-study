package com.kjh.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor // 모든 필드를 한꺼번에 받는 생성자 생성
@NoArgsConstructor	// 기본 생성자 생성
@Builder	// builder Type으로 지정
public class BoardUser implements UserDetails{
	@Id //PK로 지정
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 자동증가
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String uid;
	
	@JsonProperty(access=Access.WRITE_ONLY) // write할 때만 읽을 수 있게. Serialize
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String name;
	
	@ElementCollection(fetch=FetchType.EAGER) // 이 필드에 대해서 즉시로딩하라고 설정
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
									//  ㄴ> () -> new SimpleGrantedAuthority();
	}
	
	@JsonProperty(access=Access.WRITE_ONLY)
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@JsonProperty(access=Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonExpired();
	}

	@JsonProperty(access=Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return UserDetails.super.isAccountNonLocked();
	}

	@JsonProperty(access=Access.WRITE_ONLY)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return UserDetails.super.isCredentialsNonExpired();
	}

	@JsonProperty(access=Access.WRITE_ONLY)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return UserDetails.super.isEnabled();
	}
	
	
	
}
