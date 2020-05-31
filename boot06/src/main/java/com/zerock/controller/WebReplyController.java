package com.zerock.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerock.domain.WebBoard;
import com.zerock.domain.WebReply;
import com.zerock.repository.WebReplyRepository;

import lombok.extern.java.Log;
import net.bytebuddy.asm.Advice.Origin;

/**
 * 서비스 계층을 생략하고, 컨트롤러에서 바로 Persistence 영역을 제어할 것임
 * @author JongYoung
 *
 */
@Log
@RestController
@RequestMapping("/replies/*")
public class WebReplyController {

	@Autowired
	private WebReplyRepository replyRepo;
	
	@Transactional
	@PostMapping("/{bno}")
	public ResponseEntity<List<WebReply>> addReply(@RequestBody WebReply newReply, @PathVariable("bno") Long bno) {
		log.info("-------- 댓글 등록 요청 ----------");
		log.info("게시글 번호 : " + bno);
		log.info("댓글 : " + newReply);
		
		// 댓글의 bno 속성을 셋팅하기 위한 게시글 객체 생성
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		newReply.setBoard(board);
		replyRepo.save(newReply);
		
		return new ResponseEntity<List<WebReply>>(getListByBoard(board), HttpStatus.CREATED);
	}
	
	@Transactional
	@DeleteMapping("/{bno}/{rno}")
	public ResponseEntity<List<WebReply>> remove(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno) {
		log.info("--------" + bno + "번 게시글의 " + rno + "번 댓글 삭제 요청 ----------");
		
		// 댓글 삭제
		replyRepo.deleteById(rno);
		
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		return new ResponseEntity<List<WebReply>>(getListByBoard(board), HttpStatus.OK);
	}
	
	@Transactional
	@PutMapping("/{bno}")
	public ResponseEntity<List<WebReply>> modify(@PathVariable("bno") Long bno, @RequestBody WebReply reply) {
		log.info("--------- 댓글 수정 요청 -------------");
		
		replyRepo.findById(reply.getRno()).ifPresent(origin -> {
			log.info("댓글 내용을 수정 합니다 : (" + reply.getReplyText() + " )");
			origin.setReplyText(reply.getReplyText());
			
			replyRepo.save(origin);	// 변경 사항 반영
			
			log.info(" ----------- 댓글 수정 완료 ------------");
		});
		
		// 댓글 목록 조회를 위한 게시글 객체 생성
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		return new ResponseEntity<List<WebReply>>(getListByBoard(board), HttpStatus.CREATED);
	}
	
	@GetMapping("/{bno}")
	public ResponseEntity<List<WebReply>> getReplies(@PathVariable("bno") Long bno) {

		log.info("------------" + bno + "번 게시글의 댓글 목록 조회 요청 ---------------");
		WebBoard board = new WebBoard();
		board.setBno(bno);
		return new ResponseEntity<List<WebReply>>(getListByBoard(board), HttpStatus.OK);
	}
	
	/**
	 * 주어진 게시글의 댓글 목록 조회
	 * @param board 게시글
	 * @return 댓글 목록
	 * @throws RuntimeException
	 */
	private List<WebReply> getListByBoard(WebBoard board) throws RuntimeException{
		log.info("------- getListByBoard() : 댓글 목록을 조회합니다 -----------");
		return replyRepo.getRepliesOfBoard(board);
	}
}
