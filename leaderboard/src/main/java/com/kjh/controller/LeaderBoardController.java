package com.kjh.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjh.domain.LeaderBoardRow;
import com.kjh.service.GameService;
import com.kjh.service.LeaderBoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {
	private final LeaderBoardService leaderBoardService;

	public LeaderBoardController(final LeaderBoardService leaderBoardService) {
		this.leaderBoardService = leaderBoardService;
	}
	
	@GetMapping
	public List<LeaderBoardRow> getLeaderBoard(){
		log.info("getLeaderBoard() -- 호출");
		return leaderBoardService.getCurrentLeaderBoard();
	}
	
	
}
