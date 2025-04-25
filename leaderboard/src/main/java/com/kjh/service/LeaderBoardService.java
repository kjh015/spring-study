package com.kjh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kjh.domain.LeaderBoardRow;
import com.kjh.repository.ScoreCardRepository;

@Service
public class LeaderBoardService {
	private final ScoreCardRepository scoreCardRepository;

	LeaderBoardService(ScoreCardRepository scoreCardRepository) {
		this.scoreCardRepository = scoreCardRepository;
	}

	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}
	
}
