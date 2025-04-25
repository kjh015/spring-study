package com.kjh.domain;

import com.kjh.common.RockPaperScissors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data	//@Getter, @Setter, @ToString 등 종합모음
@Entity
@RequiredArgsConstructor
public class Rps {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rpsplay_id")
	private Long id;
	
	private final RockPaperScissors challenge;	// 사용자가 낸 것 저장
	
	Rps(){
		this(null);
	}
}
