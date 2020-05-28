package com.zerock.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.ToString;

/**
 * 페이징 처리에 필요한 정보를 담은 클래스
 * @author JongYoung
 *
 */
@Getter
@ToString(exclude = "pageList")
public class PageMaker<T> {
	
	private Page<T> result; // 해당하는 페이지의 결과 데이터들을 가진 변수
	
	private Pageable prevPage;	// '이전'으로 이동하는데 필요한 정보를 가진 Pageable 객체
	private Pageable nextPage;  // '다음'으로 이동하는데 필요한 정보를 가진 Pageable 객체
	
	private int currentPageNum; // 현재 페이지의 페이지 번호
	private int totalPageNum;	// 전체 페이지의 총 페이지 번호
	
	private Pageable currentPage;	// 현제 페이지의 정보를 담고 있는 Pageable 객체
	
	// 각각의 페이지는 pageable 객체로서 표현 됨
	private List<Pageable> pageList;	// 시작 페이지 ~ 끝 페이지에 해당하는 pagealbe 들의 리스트
	
	// 특정 페이지의 결과 데이터들을 담고 있는 page<T>를 매개변수로 받는 생성자
	public PageMaker(Page<T> result) {
		this.result = result;
		this.currentPage = result.getPageable();	// 현재 페이지의 pageable 객체를 받아옴
		this.currentPageNum = currentPage.getPageNumber() + 1;
		this.totalPageNum = result.getTotalPages();
		this.pageList = new ArrayList<Pageable>();
		
		// 시작 페이지 ~ 끝 페이지 계산
		calcPages();
	}

	private void calcPages() {
		
		// 끝 페이지 번호 계산
		int tempEndNum = (int)(Math.ceil(this.currentPageNum/10.0) * 10);
		
		// 시작 페이지 번호 계산, 총 10개의 페이지를 보여준다고 가정
		int startNum = tempEndNum - 9;
		
		// 현재 페이지의 pageable 객체를 startPage로 초기화
		Pageable startPage = currentPage;
		
		// 시작 페이지의 pageable 객체로 이동 할 것임
		for(int i = startNum; i < currentPageNum; i++) {
			// 시작 페이지 번호 ~ 현재 페이지 번호 만큼 이전의 pageable 객체로 이동
			startPage = startPage.previousOrFirst();
		}
		
		// 시작 페이지 번호가 0 페이지 보다 같거나 작지 않은 경우, prevPage를 할당
		this.prevPage = startPage.getPageNumber() <= 0 ? null : startPage.previousOrFirst();
		
		// 전체 페이지의 마지막 번호가 끝 페이지 번호 보다 작을 경우에는 마지막 페이지 번호를 전체 페이지의 마지막 번호로 설정
		// ex) 전체 페이지는 35, 끝 페이지 번호는 40인 경우, 마지막 페이지 번호 = 35로 설정 
		
		if(this.totalPageNum < tempEndNum) {
			tempEndNum = this.totalPageNum;
			this.nextPage = null;	// 마지막 페이지 이므로 다음 페이지는 할당하지 않음
		}
		
		// 시작 페이지 번호 ~ 끝 페이지 번호에 해당하는 pageable 객체를 pagelist에 추가
		for(int i = startNum; i <= tempEndNum; i++) {
			// start 페이지 부터 시작해서 끝 페이지까지 pageable 객체를 추가
			pageList.add(startPage);
			startPage = startPage.next();
		}
		
		System.out.println("시작 페이지 : " + startNum);
		System.out.println("끝 페이지 : " + tempEndNum);
		
		this.nextPage = startPage.getPageNumber() + 1 < totalPageNum ? startPage : null;
	}
	
}
