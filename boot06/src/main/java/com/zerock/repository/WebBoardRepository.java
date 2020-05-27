package com.zerock.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.zerock.domain.WebBoard;

public interface WebBoardRepository  extends CrudRepository<WebBoard, Long>, QuerydslPredicateExecutor<WebBoard>{

}
