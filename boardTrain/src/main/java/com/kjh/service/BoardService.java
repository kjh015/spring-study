package com.kjh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kjh.entity.TBoard;
import com.kjh.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;
	
	public List<TBoard> selectAllArticles() throws DataAccessException{
		return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
	public TBoard viewArticles(int articleNo) throws DataAccessException {
		Optional<TBoard> opt = boardRepository.findById(articleNo);
		TBoard tboard = null;
		if(opt.isPresent()) {
			tboard = opt.get();
		}
		return tboard;
	}
	
	
	
	
}
