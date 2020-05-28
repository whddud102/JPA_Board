package com.zerock.vo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Paging 처리를 위한 VO(Value Object) 클래스
 * @author JongYoung
 *
 */
public class PageVO {
	// satatic : 클래스의 멤버 변수로 설정, final = 수정 불가하도록 설정
	private static final int DEFAULT_SIZE = 10;	// 기본 데이터 개수
	private static final int DEFAULT_MAX_SZIE = 50;	// 최대 데이터 개수
	
	private int page;
	private int size;
	
	public PageVO() {
		// 기본 page와 size 값을 기본 생성자에서 지정
		this.page = 1;
		this.size = DEFAULT_SIZE;
	}
	
	public int getPage() {
		return page;
	}

	public int getSize() {
		return size;
	}
	
	// size와 page는 특정 값을 넘지않도록 setting
	public void setSize(int size) {
		// 기본 값 보다 작거나 최대값 보다 크면 기본 값으로 설정
		this.size = size < DEFAULT_SIZE || size > DEFAULT_MAX_SZIE ? DEFAULT_SIZE : size;
	}

	public void setPage(int page) {
		// 0 이하의 페이지를 입력하면 1로 지정
		this.page = page < 0 ? 1 : page; 
	}
	
	// '...'은 가변 파라미터 = 파라미터의 개수가 가변적임
	public Pageable makePageable(int direction, String... props) {
		Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
		
		return PageRequest.of(this.page - 1, this.size, dir, props);
	}
}
