package com.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/boards/")
public class WebBoardController {
	
	@GetMapping("/list")
	public void list() {
		log.info("------ 게시글 목록 페이지 요청 ------");
	}
}
