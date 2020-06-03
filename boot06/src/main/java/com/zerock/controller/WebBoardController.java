package com.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zerock.domain.WebBoard;
import com.zerock.persistence.CustomCrudRepository;
import com.zerock.vo.PageMaker;
import com.zerock.vo.PageVO;

import lombok.extern.java.Log;

@Log
@Controller
@RequestMapping("/boards/")
public class WebBoardController {
	
	// 동적으로  사용자 정의 쿼리를 생성하는 사용자 정의 리포지터리를 주입 받도록 변경
	@Autowired
	private CustomCrudRepository repo;
	
	@GetMapping("/list")
	public void list(PageVO vo, Model model) {	// PageVO 객체를 파라미터로 수집
		
		Pageable page = vo.makePageable(0, "bno");	// 전달 받은 파라미터를 이용해서 paging 처리에 필요한 pageable 생성 , bno 기준 내림차순 정렬
		
		// 동적으로 쿼리를 생성해서 결과를 받아 오는 getCustomPage() 이용
		Page<Object[]> result = repo.getCustomPage(vo.getType(), vo.getKeyword(), page);
		
		log.info("------ 게시글 목록 페이지 요청 ------");
		log.info("PageVO : " + page);
		log.info("게시글 목록 : " + result);
		log.info("TOTAL PAGE NUMER : " + result.getTotalPages());
		
		// pageMaker를 뷰에 전달
		model.addAttribute("result", new PageMaker<>(result));
	}
	
	
	@GetMapping("/register")
	public void registerGET() {
		log.info("---- 게시글 등록 화면 요청 -----");
	}
	
	@PostMapping("/register")
	public String registerPOST(@ModelAttribute("vo") WebBoard vo, RedirectAttributes rttr) {
		log.info("-------- 게시글을 등록 합니다 ---------");
		log.info(vo.toString());
		
		repo.save(vo);
		
		log.info("---- 게시글 등록 완료 ------");
		
		rttr.addFlashAttribute("msg", "success");
		
		return "redirect:/boards/list";
	}
	
	@GetMapping("/view")
	public void view(Long bno, @ModelAttribute("pageVO") PageVO vo, Model model) {
		log.info("------------ " + bno + "번 게시글 조회 요청 -----------");
		
		repo.findById(bno).ifPresent(board -> {
			model.addAttribute("board", board);
		});
	}
	
	@Secured(value = {"BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
	@GetMapping("/modify")
	public void modify(Long bno, @ModelAttribute("pageVO") PageVO vo, Model model) {
		log.info("---------- 게시글 수정 요청 ----------");
		
		repo.findById(bno).ifPresent(board -> {
			model.addAttribute("board", board);
		});
	}
	
	@Secured(value = {"BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
	@PostMapping("/delete")
	public String delete(Long bno, PageVO vo, RedirectAttributes rttr) {
		log.info("------- 게시글 삭제 요청 ------------");
		
		repo.deleteById(bno);
		
		rttr.addFlashAttribute("msg", "deleted");
		rttr.addAttribute("page", vo.getPage());
		rttr.addAttribute("size", vo.getSize());
		rttr.addAttribute("type", vo.getType());
		rttr.addAttribute("keyword", vo.getKeyword());
		
		return "redirect:/boards/list";
	}
	
	
	@Secured(value = {"BASIC", "ROLE_MANAGER", "ROLE_ADMIN"})
	@PostMapping("/modify")
	public String modifyPost(WebBoard board, PageVO vo, RedirectAttributes rttr) {
		log.info("-------- " + board.getBno() + "번 게시글 수정 요청 ------------");
		
		repo.findById(board.getBno()).ifPresent(origin -> {
			origin.setTitle(board.getTitle());
			origin.setContent(board.getContent());
			
			repo.save(origin);
			
			rttr.addFlashAttribute("msg", "modified");
			rttr.addAttribute("bno", board.getBno());
			
		});
		
		rttr.addAttribute("page", vo.getPage());
		rttr.addAttribute("size", vo.getSize());
		rttr.addAttribute("type", vo.getType());
		rttr.addAttribute("keyword", vo.getKeyword());
		
		return "redirect:/boards/view";
	}
}
