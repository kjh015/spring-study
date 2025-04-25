package com.kjh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kjh.domain.LeaderBoardRow;
import com.kjh.domain.ScoreCard;

public interface ScoreCardRepository extends JpaRepository<ScoreCard, Long> {
	@Query("SELECT SUM(s.score) FROM com.kjh.domain.ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
	int getTotalScoreForUser(@Param("userId") final Long userId);
	
	@Query("SELECT NEW com.kjh.domain.LeaderBoardRow(s.userId, s.alias, SUM(s.score)) "
			+ "FROM com.kjh.domain.ScoreCard s "
			+ "GROUP BY s.userId, s.alias ORDER BY SUM(s.score) DESC LIMIT 10")
	List<LeaderBoardRow> findFirst10();
	
	List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}
