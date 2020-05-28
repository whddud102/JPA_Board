package com.zerock;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.zerock.domain.WebBoard;
import com.zerock.repository.WebBoardRepository;

import lombok.extern.java.Log;

@Log
@Commit // 테스트 결과를 DB에 반영하도록 설정
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebBoardRepositoryTests {

	@Autowired
	private WebBoardRepository repo;

	// 더미 게시글 데이터 추가
	@Test
	public void insertBoardDummies() {
		IntStream.range(0, 300).forEach(i -> {
			WebBoard board = new WebBoard();

			board.setTitle("sample Board Title " + i);
			board.setContent("Content Sample ... " + i + "of Board");
			board.setWriter("user0" + (i % 10));

			repo.save(board);
		});
	}

	@Test
	public void testList1() {
		// bno 속성을 기준으로 내림차순 정렬하는 Pageable 구현체 생성
		Pageable pageable = PageRequest.of(0, 20, Direction.DESC, "bno");

		Page<WebBoard> result = repo.findAll(repo.makePredicate(null, null), pageable);

		log.info("PAGE : " + result.getPageable());

		log.info("--------------------------------------");
		result.getContent().forEach(board -> log.info("" + board));
	}

	@Test
	public void testList2() {
		// bno 속성을 기준으로 내림차순 정렬하는 Pageable 구현체 생성
		Pageable pageable = PageRequest.of(0, 20, Direction.DESC, "bno");

		// 제목에 10이 들어가는 게시글 검색
		Page<WebBoard> result = repo.findAll(repo.makePredicate("t", "10"), pageable);

		log.info("PAGE : " + result.getPageable());

		log.info("--------------------------------------");
		result.getContent().forEach(board -> log.info("" + board));
	}

}
