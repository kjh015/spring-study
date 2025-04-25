package com.kjh.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Component
public class Board {
	@Id //PK로 지정
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 자동증가
	@Column(name="article_no")
	private Integer id;
	
	@Column(length=100)	//최대 길이 제한
	private String title;
	
	@Column(length=2000)	//최대 길이 제한
	private String content;
}
