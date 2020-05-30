package com.zerock;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zerock.domain.WebBoard;
import com.zerock.domain.WebReply;
import com.zerock.repository.WebReplyRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebReplyRepositoryTests {

	@Autowired
	private WebReplyRepository repo;
	
	@Test
	public void testInsertReplies() {
		Long[] targetBoardsNum = {909L, 908L, 907L};
		
		Arrays.stream(targetBoardsNum).forEach(bno -> {
			
			// 테스트 대상 게시글 객체 생성 & 필요한 속성(bno)만 셋팅
			WebBoard board = new WebBoard();
			board.setBno(bno);
			
			// 게시글 당 10개의 댓글 데이터 삽입
			IntStream.range(0, 10).forEach(i -> {
				WebReply reply = new WebReply();
				reply.setReplyText("테스트 댓글" + i);
				reply.setReplyer("replyer" + i);
				reply.setBoard(board);
				
				repo.save(reply);
			});
		});
		
	}
}
