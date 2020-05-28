package com.zerock.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zerock.vo.PageVO;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/boards/")
public class WebBoardController {
	
	@GetMapping("/list")
	public void list(PageVO vo) {	// PageVO 객체를 파라미터로 수집
		
		Pageable page = vo.makePageable(0, "bno");	// 전달 받은 파라미터를 이용해서 paging 처리에 필요한 pageable 생성 , bno 기준 내림차순 정렬
		
		log.info("------ 게시글 목록 페이지 요청 ------");
		log.info("PageVO : " + page);
	}
}
