package com.zerock.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.zerock.domain.QWebBoard;
import com.zerock.domain.WebBoard;

public interface WebBoardRepository  extends CrudRepository<WebBoard, Long>, QuerydslPredicateExecutor<WebBoard>{

	

	/**
	 * 검색 키워드와, 검색 타입을 전달 받아서 Predicate 객체를 생성해주는 메서드
	 * @param type 검색 타입
	 * @param keyword 검색 키워드
	 * @return Predicate 객체
	 */
	// Java 8 에서는 default 키워드를 이용해서 인터페이스의 메서드의 내용을 직접 구현 가능
	public default Predicate makePredicate(String type, String keyword) {
		// Predicate 객체는 Boolean Builder를 통해 생성
		BooleanBuilder builder = new BooleanBuilder();
		
		// Qdomain 클래스를 이용해 Qdomain 객체에 접근
		QWebBoard board = QWebBoard.webBoard;

		// bno > 0 이상에 해당하는 데이터를 가져오는 조건 추가 - And 조건
		builder.and(board.bno.gt(0));		// board 객체의 bno 가 0보다 크냐? 조건 추가
		
		return builder;
	}
}
