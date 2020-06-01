package com.zerock.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 동적으로 JPQL을 생성하는 기능을 가지는, 사용자 정의 인터페이스 작성
 * @author JongYoung
 *
 */
public interface CustomWebBoard {
	/**
	 * 검색 타입, 키워드, 페이지 정보에 따라 동적으로 JPQL을 생성할 메서드
	 * @param type 검색 타입
	 * @param keyword 검색 키워드
	 * @param page 페이지 정보
	 * @return 동적으로 JPQL문 실행 후 반환 된 결과
	 */
	public Page<Object[]> getCustomPage(String type, String keyword, Pageable page);
}
