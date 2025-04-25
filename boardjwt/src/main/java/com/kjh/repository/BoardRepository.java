package com.kjh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjh.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}
