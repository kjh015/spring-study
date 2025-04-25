package com.kjh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjh.entity.BoardUser;

public interface BoardUserRepository extends JpaRepository<BoardUser, Long>{
	BoardUser getByUid(String uid);
}
