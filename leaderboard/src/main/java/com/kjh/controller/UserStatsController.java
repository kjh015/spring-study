package com.kjh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kjh.domain.GameStats;
import com.kjh.service.GameService;

@RestController
@RequestMapping("/stats")
public class UserStatsController {
	private final GameService gameService;

	public UserStatsController(final GameService gameService) {
		this.gameService = gameService;
	}
	
	@GetMapping
	public GameStats getStatsForUser(@RequestParam final Long userId) {
		
		return gameService.retrieveStatsForUser(userId);
	}
	
	
}
