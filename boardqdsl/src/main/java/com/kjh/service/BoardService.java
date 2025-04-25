package com.kjh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.kjh.entity.QBoard;
import com.kjh.entity.QQBoard;
import com.kjh.repository.BoardRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // lombok이 해당 class에 final로 되어있는 필드의 생성자를 자동으로 생성. 이후 자동 생성자주입
public class BoardService {
	private final BoardRepository boardRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<QBoard> listArticles() throws DataAccessException{
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QQBoard qqboard = QQBoard.qBoard;
//		List<QBoard> boardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); //id를 기준으로 desc 정렬
		return queryFactory.selectFrom(qqboard).orderBy(qqboard.writeDate.desc()).fetch();
	}
	public void addArticle(QBoard qboard) throws DataAccessException {		
		boardRepository.save(qboard);
	}
	public QBoard viewArticle(int articleNo) throws DataAccessException {
		QQBoard qqboard = QQBoard.qBoard;
		Predicate predicate = qqboard.id.eq(articleNo);
//		Optional<QBoard> optionalBoard = boardRepository.findById(articleNo); // 변수명이 id 인것을 사용
		QBoard qboard = null;
		Optional<QBoard> optionalBoard = boardRepository.findOne(predicate);
		if(optionalBoard.isPresent()) {
			qboard = optionalBoard.get();
		}
		return qboard;
	}	
	public void editArticle(QBoard inQBoard) throws DataAccessException {	
		QQBoard qqboard = QQBoard.qBoard;
		Predicate predicate = qqboard.id.eq(inQBoard.getId());
		QBoard qboard = null;
		Optional<QBoard> optionalBoard = boardRepository.findOne(predicate); // lombok이 만든 getter method
		
		if(optionalBoard.isPresent()) {
			qboard = optionalBoard.get();
			qboard.setTitle(inQBoard.getTitle());
			qboard.setContent(inQBoard.getContent());
			boardRepository.save(qboard);
		}
	}
	public void removeArticle(int articleNo) throws DataAccessException {
		boardRepository.deleteById(articleNo);
	}
}
