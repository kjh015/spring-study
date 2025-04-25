package com.kjh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kjh.entity.Board;
import com.kjh.repository.BoardRepository;

@Service
public class BoardService {
	private final BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	public List<Board> listArticles() throws DataAccessException{
		List<Board> boardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); //id를 기준으로 desc 정렬
		return boardList;
	}
	public void addArticle(Board board) throws DataAccessException {
		
		boardRepository.save(board);
	}
	public Board viewArticle(int articleNo) throws DataAccessException {
		Optional<Board> optionalBoard = boardRepository.findById(articleNo); // 변수명이 id 인것을 사용
		Board board = null;
		if(optionalBoard.isPresent()) {
			board = optionalBoard.get();
		}
		return board;
	}	
	public void editArticle(Board inBoard) throws DataAccessException {
		Optional<Board> optionalBoard = boardRepository.findById(inBoard.getId()); // lombok이 만든 getter method
		Board board = null;
		if(optionalBoard.isPresent()) {
			board = optionalBoard.get();
			board.setTitle(inBoard.getTitle());
			board.setContent(inBoard.getContent());
			boardRepository.save(board);
		}
	}
	public void removeArticle(int articleNo) throws DataAccessException {
		boardRepository.deleteById(articleNo);
	}
}
