package com.kjh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kjh.entity.TBoard;

public interface BoardRepository extends JpaRepository<TBoard, Integer>{

}
