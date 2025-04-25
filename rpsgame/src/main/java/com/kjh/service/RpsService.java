package com.kjh.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.kjh.common.GameResult;
import com.kjh.common.RockPaperScissors;
import com.kjh.common.RpsRule;
import com.kjh.domain.RpsChallenge;
import com.kjh.domain.User;
import com.kjh.event.EventDispatcher;
import com.kjh.event.RpsSolvedEvent;
import com.kjh.repository.RpsChallengeRepository;
import com.kjh.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RpsService {
	private final RandomGeneratorService randomGeneratorService;
	private final RpsChallengeRepository rpsChallengeRepository;
	private final UserRepository userRepository;
	private final EventDispatcher eventDispatcher;
	
	private RockPaperScissors createRandomRps() {
		return randomGeneratorService.getRockPaperScissors();
	}
	
	private GameResult checkScore(RockPaperScissors userRps, RockPaperScissors computerRps) {
		return RpsRule.checkMap.get(userRps).get(computerRps);
	}
	
	@Transactional	// 오류나면 롤백. 트랜젝션 동작하도록.
	public Map<String, String> checkChallenge(RpsChallenge rpsChallenge){
		Map<String, String> map = new HashMap<String, String>();
		Optional<User> user = userRepository.findByAlias(rpsChallenge.getUser().getAlias());
		
		Assert.isNull(rpsChallenge.getGameResult(), "완료된 상태를 보낼 수 없습니다!!");	//Null 이어야함. Null 아니면 exception 문자열 출력
		RockPaperScissors computerChoice = createRandomRps();
		GameResult gameResult = checkScore(rpsChallenge.getRps().getChallenge(), computerChoice);
		
															//orElse: Null일 경우(신규유저)
		RpsChallenge checkedChallenge = new RpsChallenge(user.orElse(rpsChallenge.getUser()), rpsChallenge.getRps(), computerChoice, gameResult);
		
		rpsChallengeRepository.save(checkedChallenge);
		
		eventDispatcher.send(new RpsSolvedEvent(checkedChallenge.getId(), checkedChallenge.getUser().getId(),
				checkedChallenge.getUser().getAlias(), checkedChallenge.getGameResult().getCommentary()));	// DB -> 이벤트 발생 -> 클라
		
		map.put("opponent", computerChoice.getCommentary());
		map.put("outcome", checkedChallenge.getGameResult().getCommentary());
		map.put("userId", "" + checkedChallenge.getUser().getId());
		
		return map;
	}
	
	public List<RpsChallenge> getStatsForUser(String userAlias){
		return rpsChallengeRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
	}
}
