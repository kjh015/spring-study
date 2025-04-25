package com.kjh.domain;

import com.kjh.common.GameResult;
import com.kjh.common.RockPaperScissors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class RpsChallenge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private final User user;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "rpsplay_id")
	private final Rps rps;	

	private final RockPaperScissors opponent;
	
	private final GameResult gameResult;
	
	RpsChallenge() {
		user = null;
		rps = null;;
		opponent = null;;
		gameResult = null;;
	}
}
