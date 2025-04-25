package com.kjh.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("boardDao")
public interface BoardDao {
	public List<NoticeBoard> selectAllArticles() throws DataAccessException; //메소드 이름과 noticeBoard의 select태그 id와 이름을 같게하여 자동매칭
	public void insertArticle(NoticeBoard noticeBoard) throws DataAccessException;
	public NoticeBoard selectArticle(int articleNo) throws DataAccessException;
	public void updateArticle(NoticeBoard noticeBoard) throws DataAccessException;
	public void deleteArticle(int articleNo) throws DataAccessException;
}
