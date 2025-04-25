package com.kjh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kjh.common.RockPaperScissors;
import com.kjh.domain.Rps;
import com.kjh.domain.RpsChallenge;
import com.kjh.domain.User;
import com.kjh.dto.RequestDto;
import com.kjh.dto.ResultDto;
import com.kjh.service.RpsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j		// lombok의 log
@RequiredArgsConstructor
@RestController
@RequestMapping("/results")
public class RpsChallengeController {
	private final RpsService rpsService;
	
	private RockPaperScissors stringToRockPaperScissors(String code) {
		RockPaperScissors result = null;
		for(RockPaperScissors rps : RockPaperScissors.values()) {
			log.info("RockPaperScissors value: " + rps.getCommentary());
			if(rps.getCommentary().equals(code)) {
				result = rps;
				break;
			}
		}
		return result;
	}
	
	@PostMapping
	Map<String, String> postResult(@RequestBody RequestDto dto){
		User user = new User(dto.userAlias());
		log.info("userChoice: " + dto.userChoice());
		Rps rps = new Rps(stringToRockPaperScissors(dto.userChoice()));
		
		RpsChallenge rpsChallenge = new RpsChallenge(user, rps, null, null);
		Map<String, String> map = rpsService.checkChallenge(rpsChallenge);
		log.info("outcome: " + map.get("outcome"));
		log.info("opponent: " + map.get("opponent"));
		return map;
	}
	
	@GetMapping
	List<ResultDto> getStatistics(@RequestParam String alias){
		List<RpsChallenge> challenges = rpsService.getStatsForUser(alias);
		List<ResultDto> results = new ArrayList<ResultDto>();
		log.info("getStatistics results size: " + results.size());
		for(RpsChallenge challenge : challenges) {
			ResultDto result = new ResultDto(challenge.getId(), challenge.getRps().getChallenge().getCommentary(),
					challenge.getOpponent().getCommentary(), challenge.getGameResult().getCommentary(), challenge.getUser().getId());
			results.add(result);
		}
		return results;
	}
	
	
}
